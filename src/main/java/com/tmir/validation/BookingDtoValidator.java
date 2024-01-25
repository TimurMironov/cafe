package com.tmir.validation;

import com.tmir.dto.BookingDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class BookingDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BookingDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookingDTO booking = (BookingDTO) target;

        if(booking.getVisitTime().isBefore(LocalDateTime.now())){
            errors.rejectValue("visitTime", "error.message.booking.before.now");
        }
    }
}
