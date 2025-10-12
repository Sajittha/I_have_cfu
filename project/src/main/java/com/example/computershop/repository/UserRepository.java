package com.example.computershop.repository;

import com.example.computershop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // สร้างเมธอดสำหรับค้นหาผู้ใช้จาก username
    Optional<User> findByUsername(String username);
}