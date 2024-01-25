package com.tmir.controller;

import com.tmir.dto.UserDTO;
import com.tmir.entity.Role;
import com.tmir.entity.User;
import com.tmir.service.BookingService;
import com.tmir.service.UserService;
import com.tmir.validation.UserDtoValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;

    private final UserDtoValidator userDtoValidator;

    private final BookingService bookingService;

    @Autowired
    public UserController(UserService userService, UserDtoValidator userDtoValidator, BookingService bookingService) {
        this.userService = userService;
        this.userDtoValidator = userDtoValidator;
        this.bookingService = bookingService;
    }

    @RequestMapping
    public String getAllUsers(Model model){
        List<User> allUsers = userService.list();
        model.addAttribute("activeUsers", allUsers.stream().filter(User::isEnabled).sorted().toList());
        model.addAttribute("deactivatedUsers", allUsers.stream().filter(user -> !user.isEnabled()).sorted().toList());
        return "users/list";
    }

    @RequestMapping("/details/{id}")
    public String getUser(@PathVariable(name = "id") Integer id, Model model){
        User user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("bookings", bookingService.findBookingByUserUsername(user.getUsername()));
        return "users/details";
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id){
        userService.delete(id);
        return "redirect:/users";
    }

    @RequestMapping("/new")
    public String createNewUser(Model model){
        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", Arrays.asList(Role.values()));
        return "users/register";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute(name = "user") @Valid UserDTO userDTO, BindingResult bindingResult, Model model){
        userDtoValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("roles", Arrays.asList(Role.values()));
            return "users/register";

        } else {
            User user = userService.convertToUserFromUserDTO(userDTO);
            userService.save(user);
            return "redirect:/users";
        }
    }

    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id, Model model){
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", Arrays.asList(Role.values()));
        return "users/editForm";
    }

    @RequestMapping(value = "/saveEdited", method = RequestMethod.POST)
    public String saveEditedUser(@ModelAttribute(name = "user") User user){
        if(user.getPassword().equals(userService.getById(user.getId()).getPassword())){
            userService.update(user);
        } else {
            userService.save(user);
        }
            return "redirect:/users";
    }

    @RequestMapping("/disable/{id}")
    public String disableUser(@PathVariable(name = "id") Integer id){
        User user = userService.getById(id);
        user.setEnabled(false);
        userService.update(user);
        return "redirect:/users/details/{id}";
    }

    @RequestMapping("/enable/{id}")
    public String enableUser(@PathVariable(name = "id") Integer id){
        User user = userService.getById(id);
        user.setEnabled(true);
        userService.update(user);
        return "redirect:/users/details/{id}";
    }
}
