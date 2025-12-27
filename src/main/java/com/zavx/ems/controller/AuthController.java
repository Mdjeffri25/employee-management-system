package com.zavx.ems.controller;

import com.zavx.ems.model.User;
import com.zavx.ems.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Passwordless / OTP Flow Concept
    @GetMapping("/otp/request")
    public String showOtpRequestForm() {
        return "otp_request";
    }

    @PostMapping("/otp/send")
    public String sendOtp(@RequestParam("email") String email, Model model) {
        User user = userService.findByEmail(email);
        if (user != null) {
            userService.generateOneTimePassword(user);
            model.addAttribute("message", "OTP sent to your email (Check server logs for demo).");
            model.addAttribute("email", email);
            return "otp_verify";
        } else {
            model.addAttribute("error", "Email not found.");
            return "otp_request";
        }
    }

    @PostMapping("/otp/verify")
    public String verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otp, Model model) {
        User user = userService.findByEmail(email);
        if (user != null && userService.verifyOneTimePassword(user, otp)) {
            // In a real app, you would manually set the SecurityContext here
            // For this structural demo, we redirect to login with a success message
            return "redirect:/login?otpSuccess";
        } else {
            model.addAttribute("error", "Invalid or expired OTP.");
            model.addAttribute("email", email);
            return "otp_verify";
        }
    }
}
