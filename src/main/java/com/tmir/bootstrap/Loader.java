package com.tmir.bootstrap;

import com.tmir.entity.Role;
import com.tmir.entity.User;
import com.tmir.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Loader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;

    public Loader(UserService userService) {
        this.userService = userService;
    }

    // При запуске приложения создается пользователь super с паролем super
    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent event) {
        User user = new User();
        user.setUsername("super");
        user.setPassword("super");
        user.setAuthorities(List.of(Role.valueOf("ROLE_SUPER")));
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        userService.save(user);
    }
}
