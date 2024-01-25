package com.tmir.dto;

import com.tmir.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UserDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String repeatedPassword;

    @NotNull
    private List<Role> authorities;

    private Boolean active;

}
