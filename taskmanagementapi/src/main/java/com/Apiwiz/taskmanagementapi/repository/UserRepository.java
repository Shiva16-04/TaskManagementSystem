package com.Apiwiz.taskmanagementapi.repository;

import com.Apiwiz.taskmanagementapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User>findByEmailId(String emailId);
}
