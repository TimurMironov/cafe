package com.tmir.validation;

import com.tmir.dto.UserDTO;
import com.tmir.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserDtoValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserDtoValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;

        if (!userDTO.getPassword().equals(userDTO.getRepeatedPassword())){
            System.out.println(userDTO.getPassword());
            System.out.println(userDTO.getRepeatedPassword());
            errors.rejectValue("repeatedPassword", "error.message.passwordsNotEquals");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.message.empty");
        if (userDTO.getPassword().length()<6 || userDTO.getPassword().length()>32){
            errors.rejectValue("password", "error.message.password.length");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Длина пароля должна быть от 6 до 32 символов");
        if (userDTO.getUsername().length()<6 || userDTO.getUsername().length()>32){
            errors.rejectValue("username", "error.message.username.length");
        }

        if (userService.IsUserExistByUsername(userDTO.getUsername())){
            errors.rejectValue("username", "error.message.user.exist");
        }
    }
}
