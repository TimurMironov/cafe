package com.tmir.dto;

import com.tmir.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReportDTO {

    private LocalDateTime visitTimeFrom;

    private LocalDateTime visitTimeTo;

    private Boolean cancelled;

    private User user;
}
