package com.example.nutrition.domain.repository;

import com.example.nutrition.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String loginId);

    Optional<User> findByLoginId(String loginId);
}
