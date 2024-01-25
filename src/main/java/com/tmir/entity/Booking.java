package com.tmir.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@jakarta.persistence.Table(name = "_bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "visitor")
    private String visitorName;

    @Column(name = "date_time_of_booking")
    private LocalDateTime dateTimeOfBooking;

    @ManyToOne
    @JoinColumn(name = "_table", referencedColumnName = "id")
    private Table table;

    @Column(name = "visit_time")
    private LocalDateTime visitTime;

    @Column(name = "for_how_long")
    private Long forHowLong;

    @ManyToOne
    @JoinColumn(name = "_user", referencedColumnName = "id")
    private User user;

    @Column(name = "cancelled")
    private Boolean cancelled;

}
