package com.tmir.validation;

import com.tmir.entity.Table;
import com.tmir.repository.TableRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TableValidator implements Validator {

    private final TableRepository tableService;

    public TableValidator(TableRepository tableRepository) {
        this.tableService = tableRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Table.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Table table = (Table) target;

        if (tableService.findByNumber(table.getNumber())!=null){
            errors.rejectValue("number", "error.message.table.exist");
        }


    }


}
