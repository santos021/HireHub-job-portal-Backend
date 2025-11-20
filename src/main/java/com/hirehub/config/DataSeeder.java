package com.hirehub.config;

import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import org.springframework.boot.CommandLineRunner;

import com.hirehub.entity.Role;
import com.hirehub.entity.User;
import com.hirehub.repository.RoleRepository;
import com.hirehub.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
	private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Seed roles
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_EMPLOYER");

        // Seed admin user if not exists
        if (!userRepository.findByEmail("admin@hirehub.com").isPresent()) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@hirehub.com");
            admin.setPassword(passwordEncoder.encode("Admin@123")); // change password after first login
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
            System.out.println("Admin user created -> admin@hirehub.com / Admin@123");
        }
    }

    private void createRoleIfNotFound(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(new Role(null, roleName)));
    }

}
