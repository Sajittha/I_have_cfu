package com.example.computershop.controller;

import com.example.computershop.model.User;
import com.example.computershop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // แสดงหน้าฟอร์มสมัครสมาชิก
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // รับข้อมูลจากฟอร์มสมัครสมาชิก
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        // เข้ารหัสรหัสผ่านก่อนบันทึกลงฐานข้อมูล
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // กำหนด Role พื้นฐานให้ผู้ใช้ใหม่
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "redirect:/login";
    }

    // แสดงหน้าฟอร์ม Login
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}