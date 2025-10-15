package com.example.computershop.config;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. ปิด CSRF
            .csrf(csrf -> csrf.disable())

            // 2. กำหนดกฎการเข้าถึง
            .authorizeHttpRequests(authorize -> authorize
                // กฎข้อที่ 1: URL ที่ขึ้นต้นด้วย /admin ต้องมีสิทธิ์ ROLE_ADMIN
                .requestMatchers("/admin/**").hasAuthority("ADMIN")

                // กฎข้อที่ 2: URL เหล่านี้ ทุกคนสามารถเข้าได้
                .requestMatchers("/", "/register", "/product/**", "/api/**", "/css/**", "/js/**").permitAll()

                // กฎข้อที่ 3: URL อื่นๆ ที่เหลือทั้งหมด ต้อง Login ก่อน
                .anyRequest().authenticated()
            )

            // 3. กำหนดค่าหน้า Login
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(customAuthenticationSuccessHandler) // ใช้ Handler ที่เราสร้าง
                .permitAll()
            )

            // 4. กำหนดค่า Logout
            .logout(logout -> logout
                .permitAll()
            );
            
        return http.build();
    }
}