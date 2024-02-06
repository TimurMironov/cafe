package com.tmir.service;

import com.tmir.entity.Booking;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportService {

    public XSSFWorkbook generateReport(List<Booking> bookings) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");

        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.index);

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        cellStyle.setFont(font);

        int rowNumber = 0;
        Row headerRow = sheet.createRow(rowNumber++);

        int cellNumber = 0;

        Cell visitorNameHeader = headerRow.createCell(cellNumber++);
        visitorNameHeader.setCellStyle(cellStyle);

        Cell dateTimeOfBookingHeader = headerRow.createCell(cellNumber++);
        dateTimeOfBookingHeader.setCellStyle(cellStyle);

        Cell tableHeader = headerRow.createCell(cellNumber++);
        tableHeader.setCellStyle(cellStyle);

        Cell visitTimeHeader = headerRow.createCell(cellNumber++);
        visitTimeHeader.setCellStyle(cellStyle);

        Cell forHowLongHeader = headerRow.createCell(cellNumber++);
        forHowLongHeader.setCellStyle(cellStyle);

        Cell adminHeader = headerRow.createCell(cellNumber++);
        adminHeader.setCellStyle(cellStyle);

        Cell cancelledHeader = headerRow.createCell(cellNumber++);
        cancelledHeader.setCellStyle(cellStyle);

        visitorNameHeader.setCellValue("Имя гостя");
        dateTimeOfBookingHeader.setCellValue("Дата и время бронирования");
        tableHeader.setCellValue("Номер столика");
        visitTimeHeader.setCellValue("Дата и время визита");
        forHowLongHeader.setCellValue("Продолжительность визита");
        adminHeader.setCellValue("Администратор");
        cancelledHeader.setCellValue("Активность брони");


        for (Booking booking : bookings) {
            Row row = sheet.createRow(rowNumber++);
            cellNumber = 0;

            Cell visitorName = row.createCell(cellNumber++);
            Cell dateTimeOfBooking = row.createCell(cellNumber++);
            Cell table = row.createCell(cellNumber++);
            Cell visitTime = row.createCell(cellNumber++);
            Cell forHowLong = row.createCell(cellNumber++);
            Cell admin = row.createCell(cellNumber++);
            Cell cancelled = row.createCell(cellNumber++);

            String dateTimeOfBookingSTR = booking.getDateTimeOfBooking().toString().replace("T", " ");
            String visitTimeSTR = booking.getVisitTime().toString().replace("T", " ");
            String isCancelled = booking.getCancelled() ? "Отменена" : "Активна";

            visitorName.setCellValue(booking.getVisitorName());
            dateTimeOfBooking.setCellValue(dateTimeOfBookingSTR);
            table.setCellValue(booking.getTable().getNumber());
            visitTime.setCellValue(visitTimeSTR);
            forHowLong.setCellValue(booking.getForHowLong());
            admin.setCellValue(booking.getUser().getUsername());
            cancelled.setCellValue(isCancelled);
        }
        return workbook;
    }
}
