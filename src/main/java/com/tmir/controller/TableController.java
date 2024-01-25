package com.tmir.controller;

import com.tmir.entity.Booking;
import com.tmir.entity.Table;
import com.tmir.service.BookingService;
import com.tmir.service.TableService;
import com.tmir.service.TableTypeService;
import com.tmir.validation.TableValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class TableController {

    private final TableService tableService;

    private final TableTypeService tableTypeService;

    private final BookingService bookingService;

    private final TableValidator tableValidator;

    @Autowired
    public TableController(TableService tableService, TableTypeService tableTypeService, BookingService bookingService, TableValidator tableValidator) {
        this.tableService = tableService;
        this.tableTypeService = tableTypeService;
        this.bookingService = bookingService;
        this.tableValidator = tableValidator;
    }

    @RequestMapping("/tables")
    public String listTables(Model model){
        model.addAttribute("tables", tableService.list());
        return "tables/list";
    }

    @RequestMapping("/tables/details/{id}")
    public String getTableDetails(@PathVariable(name = "id") Integer id, Model model){
        model.addAttribute("table", tableService.getById(id));
        model.addAttribute("bookings", bookingService.findBookingByAfterNowAndId(id));
        return "tables/details";
    }

    @RequestMapping("/tables/delete/{id}")
    public String deleteTable(@PathVariable(name = "id") Integer id){
        tableService.delete(id);
        return "redirect:/tables";
    }

    @RequestMapping("/tables/new")
    public String createNewTable(Model model){
        model.addAttribute("table", new Table());
        model.addAttribute("tableTypes", tableTypeService.list());
        return "tables/form";
    }

    @RequestMapping("/tables/edit/{id}")
    public String updateTable(@PathVariable(name = "id") Integer id, Model model){
        model.addAttribute("table", tableService.getById(id));
        model.addAttribute("tableTypes", tableTypeService.list());
        return "tables/form";
    }

    @RequestMapping(value = "/tables/save", method = RequestMethod.POST)
    public String saveNewTable(@ModelAttribute(name = "table") @Valid Table table, BindingResult bindingResult, Model model){
        tableValidator.validate(table, bindingResult);

        if(bindingResult.hasErrors()){
            model.addAttribute("tableTypes", tableTypeService.list());
            return "tables/form";
        } else {
            tableService.save(table);
            return "redirect:/tables";
        }
    }

    @RequestMapping(value = "/tables/booking/cancel/{table}/{id}")
    public String cancelBooking(@PathVariable(name = "id") Integer id, @PathVariable(name = "table") Integer table){
        Booking booking = bookingService.getById(id);
        booking.setCancelled(true);
        bookingService.save(booking);
        return "redirect:/tables/details/{table}";
    }
}
