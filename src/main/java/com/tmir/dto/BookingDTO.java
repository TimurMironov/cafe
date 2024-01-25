package com.tmir.dto;

import com.tmir.entity.Table;
import com.tmir.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDTO {

    private Integer id;

    @NotNull
    private String visitorName;

    private LocalDateTime dateTimeOfBooking;

    @NotNull
    private Table table;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm")
    private LocalDateTime visitTime;

    @NotNull
    @Min(value = 0, message = "error.message.booking.duration")
    private Long forHowLong;

    private User user;

    private Boolean cancelled;
}
