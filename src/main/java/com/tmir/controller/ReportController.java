package com.tmir.controller;

import com.tmir.dto.ReportDTO;
import com.tmir.entity.Booking;
import com.tmir.service.BookingService;
import com.tmir.service.ReportService;
import com.tmir.service.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final BookingService bookingService;

    private final UserService userService;

    private final ReportService reportService;

    public ReportController(BookingService bookingService, UserService userService, ReportService reportService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @RequestMapping
    public String reportPage(Model model){
        model.addAttribute("report", new ReportDTO());
        model.addAttribute("users", userService.list());
        return "reports/report";
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public void generateReport(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String from = request.getParameter("visitTimeFrom").replace("T", " ");
        String to = request.getParameter("visitTimeTo").replace("T", " ");
        String user = request.getParameter("user");
        String cancelledStr = request.getParameter("cancelled");

        Boolean cancelled = Boolean.valueOf(cancelledStr);

        List<Booking> bookingsForReport = bookingService.createReportList(from, to, user, cancelled);

        XSSFWorkbook report = reportService.generateReport(bookingsForReport);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=example.xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        report.write(outputStream);
        report.close();

    }

}
