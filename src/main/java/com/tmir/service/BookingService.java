package com.tmir.service;

import com.tmir.dto.BookingDTO;
import com.tmir.entity.Booking;
import com.tmir.exceptions.NotFoundException;
import com.tmir.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService implements CommonServiceMethods<Booking>{

    private final BookingRepository bookingRepository;

    private final UserService userService;

    private final TableService tableService;

    public BookingService(BookingRepository bookingRepository, UserService userService, TableService tableService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.tableService = tableService;
    }

    @Override
    public List<Booking> list() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getById(Integer id) {
        return bookingRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Бронь столика с id " + id + " не найдена"));
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void delete(Integer id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> findBookingByAfterNowAndId(Integer roomId){
        List<Booking> bookings = new ArrayList<>();
        for(Booking booking: bookingRepository.findBookingByVisitTimeAfterNowAAndId(roomId)){
            if (!booking.getCancelled()){
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public List<Booking> findBookingByUserUsername(String username){
        return bookingRepository.findBookingsByUser_Username(username);
    }

    public List<Booking> findBookingByAfterNow(){
        return bookingRepository.findBookingsByVisitTimeAfterNow();
    }

    public Booking convertToBookingFromBookingDTO(BookingDTO bookingDTO){
        return Booking.builder()
                .id(bookingDTO.getId())
                    .visitorName(bookingDTO.getVisitorName())
                        .dateTimeOfBooking(LocalDateTime.now().withNano(0))
                            .table(tableService.findByNumber(bookingDTO.getTable().getNumber()))
                                .visitTime(bookingDTO.getVisitTime())
                                    .forHowLong(bookingDTO.getForHowLong())
                                        .user(userService.getCurrentLoggedUser())
                                            .cancelled(false)
                                                .build();
    }
}
