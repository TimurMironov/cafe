package com.tmir.service;

import com.tmir.dto.UserDTO;
import com.tmir.entity.User;
import com.tmir.exceptions.NotFoundException;
import com.tmir.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements CommonServiceMethods<User>, UserDetailsService {

    private final UserRepository userRepository;


    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }


    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не удалось найти пользователя с id" + id));
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }


    public boolean IsUserExistByUsername(String username){
        User user = userRepository.findUserByUsername(username).orElse(null);
        return user != null;
    }

    public User convertToUserFromUserDTO(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setAuthorities(userDTO.getAuthorities());
        user.setPassword(userDTO.getPassword());
        user.setEnabled(userDTO.getActive());
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        return user;
    }

    public User getCurrentLoggedUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("Не удалось найти пользователя с именем" + username));
    }
}