package com.tmir.controller;

import com.tmir.dto.BookingDTO;
import com.tmir.dto.CheckFreeTablesDTO;
import com.tmir.entity.Booking;
import com.tmir.entity.Table;
import com.tmir.service.BookingService;
import com.tmir.service.TableService;
import com.tmir.validation.BookingDtoValidator;
import com.tmir.validation.CheckFreeTablesDTOValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    private final BookingDtoValidator bookingDtoValidator;

    private final TableService tableService;

    private final CheckFreeTablesDTOValidation freeTablesValidation;

    @Autowired
    public BookingController(BookingService bookingService, BookingDtoValidator bookingDtoValidator, TableService tableService, CheckFreeTablesDTOValidation freeTablesValidation) {
        this.bookingService = bookingService;
        this.bookingDtoValidator = bookingDtoValidator;
        this.tableService = tableService;
        this.freeTablesValidation = freeTablesValidation;
    }

    @RequestMapping
    public String getAllBookings(Model model){
        model.addAttribute("bookings", bookingService.findBookingByAfterNow());
        return "bookings/list";
    }

    @RequestMapping("/check")
    public String formFreeTables(Model model){
        model.addAttribute("checkFree", new CheckFreeTablesDTO());
        return "bookings/checkFreeTables";
    }

    @RequestMapping(value = "/showFree", method = RequestMethod.POST)
    public String showFreeTableByDateTime(@ModelAttribute(name = "checkFree") @Valid CheckFreeTablesDTO checkParametersFree
            , BindingResult bindingResult,  Model model){

        freeTablesValidation.validate(checkParametersFree, bindingResult);

        if (bindingResult.hasErrors()){
            return "bookings/checkFreeTables";
        } else {
            List<Table> freeTables = tableService.findFreeTable(checkParametersFree.getVisitTime()
                    , checkParametersFree.getForHowLong());
            model.addAttribute("freeTables", freeTables);

            return "bookings/showFreeTables";
        }
    }

    @RequestMapping(value = "/new/{id}", method = RequestMethod.GET)
    public String createNewBooking(@ModelAttribute("checkFree") CheckFreeTablesDTO freeTablesParam
            , @PathVariable(name = "id") Integer id, Model model) {

        BookingDTO booking = new BookingDTO();
        booking.setVisitTime(freeTablesParam.getVisitTime());
        booking.setForHowLong(freeTablesParam.getForHowLong());
        booking.setTable(tableService.getById(id));

        model.addAttribute("booking", booking);

        return "bookings/form";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveBooking(@ModelAttribute(name = "booking") @Valid BookingDTO bookingDTO
            , BindingResult bindingResult) {

        bookingDtoValidator.validate(bookingDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "bookings/form";
        } else {
            Booking booking = bookingService.convertToBookingFromBookingDTO(bookingDTO);
            bookingService.save(booking);
            return "redirect:/bookings";
        }
    }

    @RequestMapping("/delete/{id}")
    public String deleteBooking(@PathVariable(name = "id") Integer id){
        bookingService.delete(id);
        return "redirect:/bookings";
    }

    @RequestMapping("/cancell/{id}")
    public String cancelBooking(@PathVariable(name = "id") Integer id){
        Booking booking = bookingService.getById(id);
        booking.setCancelled(true);
        bookingService.save(booking);
        return "redirect:/bookings";
    }

    @RequestMapping("/active/{id}")
    public String activeBooking(@PathVariable(name = "id") Integer id){
        Booking booking = bookingService.getById(id);
        booking.setCancelled(false);
        bookingService.save(booking);
        return "redirect:/bookings";
    }
}
