package com.tmir.validation;

import com.tmir.dto.CheckFreeTablesDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class CheckFreeTablesDTOValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CheckFreeTablesDTOValidation.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CheckFreeTablesDTO freeTablesParameters = (CheckFreeTablesDTO) target;

        if(freeTablesParameters.getVisitTime().isBefore(LocalDateTime.now())){
            errors.rejectValue("visitTime", "error.message.booking.before.now");
        }
    }
}
