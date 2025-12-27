package com.zavx.ems.service;

import com.zavx.ems.model.Role;
import com.zavx.ems.model.User;
import com.zavx.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    // Modern Passwordless / OTP Concept Implementation

    private static final long OTP_VALID_DURATION = 5 * 60 * 1000; // 5 minutes

    @Override
    public void generateOneTimePassword(User user) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setOneTimePassword(otp);
        user.setOtpRequestedTime(System.currentTimeMillis());
        userRepository.save(user);

        // In a real application, send this OTP via Email/SMS
        System.out.println("OTP for " + user.getEmail() + ": " + otp);
    }

    @Override
    public boolean verifyOneTimePassword(User user, String otp) {
        if (user.getOneTimePassword() == null) {
            return false;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = user.getOtpRequestedTime();

        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            // OTP expired
            return false;
        }

        if (otp.equals(user.getOneTimePassword())) {
            // Clear OTP
            user.setOneTimePassword(null);
            user.setOtpRequestedTime(0L);
            userRepository.save(user);
            return true;
        }

        return false;
    }
}
