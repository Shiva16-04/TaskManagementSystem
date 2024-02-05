package com.Apiwiz.taskmanagementapi.models;

import com.Apiwiz.taskmanagementapi.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column( nullable = false)
    String name;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    Role role;

    @Column(nullable = false)
    int age;

    @Column(nullable = false, unique = true)
    @Email(message = "should be a valid college email address")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "should be a valid email address")
    String emailId;

    @Column(nullable = false, unique = true)
    @Pattern(regexp="[0-9]{10}", message="Should be a valid contact number")
    String contactNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Task> taskList;
}
