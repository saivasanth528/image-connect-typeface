package com.typeface.imageconnect.service;

import com.typeface.imageconnect.entity.User;
import com.typeface.imageconnect.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void createDummyUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            User user = new User();
            user.setUsername("user" + i);
            users.add(user);
        }
        userRepository.saveAll(users);
    }

    public List<User> getUsers() {
        List<User> users =  userRepository.findAll();
        Collections.sort(users, Comparator.comparingLong(User::getId));
        return users;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteAllRecords() {
        userRepository.deleteAll();
    }
}
