package com.Apiwiz.taskmanagementapi.models;

import com.Apiwiz.taskmanagementapi.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true)
    String code;

    @Column(nullable = false)
    String title;

    @Column(columnDefinition = "LONGTEXT")
    String description;

    @Column(nullable = false)
    LocalDateTime dueDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    Status status;

    @ManyToOne(cascade = CascadeType.PERSIST)
    User user;
}
