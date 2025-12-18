//package com.sandeep.E_Commerce_Project.Config;
//
//import com.sandeep.E_Commerce_Project.Models.Role;
//import com.sandeep.E_Commerce_Project.Models.User;
//import com.sandeep.E_Commerce_Project.Repositories.UserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Set;
//
//@Configuration
//public class DataInitializer {
//
//    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
//
//    @Bean
//    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder encoder) {
//        return args -> {
//            String adminEmail = "admin@gmail.com";
//
//            // Check if admin already exists to prevent duplicates
//            if (userRepository.findByEmail(adminEmail).isEmpty()) {
//
//                User admin = new User();
//                admin.setUsername("Admin");
//                admin.setEmail(adminEmail);
//                admin.setPassword(encoder.encode("admin123")); // Default Password
//                admin.setRoles(Set.of(Role.ROLE_ADMIN));       // Assign Admin Role
//                admin.setEnabled(true);                        // Ensure account is active
//
//                userRepository.save(admin);
//
//                logger.info("🚀 Default Admin User Created Successfully: {}", adminEmail);
//            } else {
//                logger.info("ℹ️ Admin User already exists. Skipping creation.");
//            }
//        };
//    }
//}