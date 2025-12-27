package com.hightech.ems.service;

import com.hightech.ems.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User save(User user);

    List<User> getAllUsers();

    User findByEmail(String email);

    void generateOneTimePassword(User user);

    boolean verifyOneTimePassword(User user, String otp);
}
