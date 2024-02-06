package com.tmir.service;

import com.tmir.dto.BookingDTO;
import com.tmir.entity.Booking;
import com.tmir.exceptions.NotFoundException;
import com.tmir.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService implements CommonServiceMethods<Booking> {

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
                .orElseThrow(() -> new NotFoundException("Бронь столика с id " + id + " не найдена"));
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void delete(Integer id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> findBookingByAfterNowAndId(Integer roomId) {
        List<Booking> bookings = new ArrayList<>();
        for (Booking booking : bookingRepository.findBookingByVisitTimeAfterNowAAndId(roomId)) {
            if (!booking.getCancelled()) {
                bookings.add(booking);
            }
        }
        return bookings;
    }

    public List<Booking> findBookingByUserUsername(String username) {
        return bookingRepository.findBookingsByUser_Username(username);
    }

    public List<Booking> findBookingByAfterNow() {
        return bookingRepository.findBookingsByVisitTimeAfterNow();
    }

    public Booking convertToBookingFromBookingDTO(BookingDTO bookingDTO) {
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

    public List<Booking> createReportList(String fromSTR, String toSTR, String user, Boolean cancelled) {

        List<Booking> allBookings = list();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (!fromSTR.isEmpty()) {
            LocalDateTime from = LocalDateTime.parse(fromSTR, dateTimeFormatter);
            allBookings = allBookings.stream().filter(booking -> booking.getVisitTime().isAfter(from))
                    .collect(Collectors.toList());
        }

        if (!toSTR.isEmpty()) {
            LocalDateTime to = LocalDateTime.parse(toSTR, dateTimeFormatter);
            allBookings = allBookings.stream().filter(booking -> booking.getVisitTime().isBefore(to))
                    .collect(Collectors.toList());
        }

        if (!user.isEmpty()) {
            Integer userId = Integer.valueOf(user);
            allBookings = allBookings.stream().filter(booking -> booking.getUser().getId().equals(userId))
                    .collect(Collectors.toList());
        }

        if (cancelled){
            allBookings = allBookings.stream().filter(Booking::getCancelled)
                    .collect(Collectors.toList());
        }
        return allBookings;
    }
}
