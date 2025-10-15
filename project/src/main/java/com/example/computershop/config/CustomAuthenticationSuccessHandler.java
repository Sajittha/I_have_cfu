package com.example.computershop.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        // ตรวจสอบ Role ของผู้ใช้ที่ Login สำเร็จ
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ADMIN".equals(auth.getAuthority())) {
                // ถ้าเป็น ADMIN, ให้ redirect ไปที่หน้า /admin
                response.sendRedirect("/admin");
                return; // จบการทำงาน
            }
        }

        // ถ้าไม่ใช่ ADMIN (เป็น Role อื่นๆ), ให้ redirect ไปที่หน้าแรก (/)
        response.sendRedirect("/");
    }
}