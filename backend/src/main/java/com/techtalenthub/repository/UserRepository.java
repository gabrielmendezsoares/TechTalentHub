package com.techtalenthub.repository;

import java.util.Optional;
import com.techtalenthub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
