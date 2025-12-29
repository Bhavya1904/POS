package com.pos.repository;

import com.pos.domain.UserRole;
import com.pos.model.Store;
import com.pos.model.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(@Email(message = "Use a valid Email") String email);

    List<User> findByStore(Store store);
    List<User> findByBranchId(Long branchId);
//    User findByEmail(String username);
}
