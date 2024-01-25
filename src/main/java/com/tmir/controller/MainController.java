package com.tmir.controller;

import com.tmir.service.BookingService;
import com.tmir.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final UserService userService;

    private final BookingService bookingService;

    @Autowired
    public MainController(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String mainController(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();
        String role = String.valueOf(authentication.getAuthorities());


        model.addAttribute("welcomeMsg", "Вы вошли в систему как " + userName);

        switch (role) {
            case ("[ROLE_SUPER]") -> {
                model.addAttribute("users", userService.list());
                return "redirect:/users";
            }
            case ("[ROLE_CAFE_ADMIN]") -> {
                model.addAttribute("bookings", bookingService.list());
                return "redirect:/bookings";
            }
        }

        return "/indexes/index";
    }


    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }
}
