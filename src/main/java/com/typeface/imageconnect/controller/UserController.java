package com.typeface.imageconnect.controller;

import com.typeface.imageconnect.entity.User;
import com.typeface.imageconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/user/{username}")
    public Optional<User> getUserByUserName(String username) {
        return userService.getUserByUserName(username);
    }

}
