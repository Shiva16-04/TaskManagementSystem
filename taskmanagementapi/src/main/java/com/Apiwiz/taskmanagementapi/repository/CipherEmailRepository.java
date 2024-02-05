package com.Apiwiz.taskmanagementapi.repository;

import com.Apiwiz.taskmanagementapi.models.CipherEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CipherEmailRepository extends JpaRepository<CipherEmail, Integer> {
    Optional<CipherEmail> findByEmailId(String emailId);
}
