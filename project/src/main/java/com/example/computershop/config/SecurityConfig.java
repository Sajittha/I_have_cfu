package com.example.computershop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // ใช้ BCrypt สำหรับเข้ารหัสรหัสผ่าน (ปลอดภัยที่สุด)
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            // เพิ่มบรรทัดนี้เข้ามา
            .requestMatchers("/admin/**").hasRole("ADMIN")
            // อนุญาตให้ทุกคนเข้าถึงหน้าเหล่านี้ได้โดยไม่ต้อง Login
            // ผมเพิ่ม "/product/**" เข้าไปด้วยเพื่อให้คนไม่ login ดูรายละเอียดสินค้าได้
            .requestMatchers("/", "/register", "/product/**", "/api/**", "/css/**", "/js/**").permitAll()
            // หน้าอื่นๆ ทั้งหมด ต้องมีการยืนยันตัวตน (Login) ก่อน
            .anyRequest().authenticated()
        )
            .formLogin(form -> form
                // กำหนดหน้า Login ของเราเอง
                .loginPage("/login")
                // URL ที่ฟอร์ม Login จะส่งข้อมูลไป
                .loginProcessingUrl("/login")
                // URL ที่จะไปหลังจาก Login สำเร็จ
                .defaultSuccessUrl("/", true)
                // อนุญาตให้ทุกคนเข้าหน้า Login ได้
                .permitAll()
            )
            .logout(logout -> logout
                // อนุญาตให้ทุกคน Logout ได้
                .permitAll()
            );
        return http.build();
    }
}