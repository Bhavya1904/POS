package com.pos.repository;

import com.pos.model.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(@Email(message = "Use a valid Email") String email);

//    User findByEmail(String username);
}
