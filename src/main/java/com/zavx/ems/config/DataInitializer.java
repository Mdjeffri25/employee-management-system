package com.zavx.ems.config;

import com.zavx.ems.model.Role;
import com.zavx.ems.model.User;
import com.zavx.ems.repository.RoleRepository;
import com.zavx.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize Roles
        if (roleRepository.findAll().isEmpty()) {
            Role adminRole = new Role("ROLE_ADMIN");
            Role hrRole = new Role("ROLE_HR");
            Role empRole = new Role("ROLE_EMPLOYEE");

            roleRepository.saveAll(Arrays.asList(adminRole, hrRole, empRole));

            // Initialize Default Admin
            User admin = new User();
            admin.setEmail("admin@zavx.tech");
            admin.setPassword(passwordEncoder.encode("admin123")); // Secure BCrypt Password
            admin.setRoles(Arrays.asList(adminRole, hrRole));

            userRepository.save(admin);

            // Initialize Default HR
            User hr = new User();
            hr.setEmail("hr@zavx.tech");
            hr.setPassword(passwordEncoder.encode("hr123"));
            hr.setRoles(Collections.singletonList(hrRole));

            userRepository.save(hr);

            // Initialize Default Employee
            User emp = new User();
            emp.setEmail("employee@zavx.tech");
            emp.setPassword(passwordEncoder.encode("employee123"));
            emp.setRoles(Collections.singletonList(empRole));

            userRepository.save(emp);

            System.out.println(
                    "Default users created: admin@zavx.tech, hr@zavx.tech, employee@zavx.tech (Password: <role>123)");
        }
    }
}
