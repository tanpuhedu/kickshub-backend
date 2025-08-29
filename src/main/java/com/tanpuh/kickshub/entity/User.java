package com.tanpuh.kickshub.entity;

import com.tanpuh.kickshub.utils.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String username;
    String password;
    String fullName;
    String phone;
    String email;

    @Enumerated
    EntityStatus status;

    @ManyToMany
    Set<Role> roles;
}
