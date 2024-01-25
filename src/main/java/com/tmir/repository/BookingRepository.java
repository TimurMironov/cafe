package com.tmir.repository;

import com.tmir.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("select b from Booking b where b.visitTime > current_timestamp and b.table.id = :id order by b.id asc " )
    List<Booking> findBookingByVisitTimeAfterNowAAndId(@Param(value = "id") Integer id);

    List<Booking> findBookingsByUser_Username(String username);

    @Query("select b from Booking b where b.visitTime > current_timestamp order by b.visitTime")
    List<Booking> findBookingsByVisitTimeAfterNow();
}
