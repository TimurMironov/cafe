package com.tmir.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CheckFreeTablesDTO {

    @NotNull
    private LocalDateTime visitTime;

    @NotNull
    @Min(value = 0, message = "Длительность бронирования не может быть отрицательной")
    private Long forHowLong;
}
