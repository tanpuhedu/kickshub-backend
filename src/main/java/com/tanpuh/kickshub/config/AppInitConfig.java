package com.tanpuh.kickshub.config;

import com.tanpuh.kickshub.entity.Role;
import com.tanpuh.kickshub.entity.User;
import com.tanpuh.kickshub.repository.RoleRepository;
import com.tanpuh.kickshub.repository.UserRepository;
import com.tanpuh.kickshub.utils.enums.EntityStatus;
import com.tanpuh.kickshub.utils.enums.PredefinedRole;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AppInitConfig {
    private final PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USERNAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
                Role adminRole = Role.builder()
                        .id(PredefinedRole.ADMIN.getId())
                        .name(PredefinedRole.ADMIN.getName())
                        .build();
                roleRepository.save(adminRole);

                Role userRole = Role.builder()
                        .id(PredefinedRole.USER.getId())
                        .name(PredefinedRole.USER.getName())
                        .build();
                roleRepository.save(userRole);

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .username(ADMIN_USERNAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .status(EntityStatus.ACTIVE)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
