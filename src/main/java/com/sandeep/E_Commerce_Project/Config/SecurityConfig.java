package com.sandeep.E_Commerce_Project.Config;

import com.sandeep.E_Commerce_Project.Repositories.UserRepository;
import com.sandeep.E_Commerce_Project.Security.JwtAuthFilter;
import com.sandeep.E_Commerce_Project.Security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepo;

    public SecurityConfig(JwtService jwtService, UserRepository userRepo) {
        this.jwtService = jwtService;
        this.userRepo = userRepo;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http

                .cors(cors -> {})   // ✅ ENABLE CORS
                // ❌ Disable CSRF (stateless REST API)
                .csrf(csrf -> csrf.disable())

                // ❌ Disable default login mechanisms
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable())

                // ✅ JWT = Stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // ✅ Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // 🔓 PUBLIC APIs
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/products/**").permitAll()

                        // 🔒 ADMIN APIs
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 🔐 USER APIs (cart, profile later)
                        .requestMatchers("/api/cart/**").hasRole("USER")

                        // 🔒 USER APIs Address
                        .requestMatchers("/api/addresses/**").hasRole("USER")

                        .requestMatchers("/api/orders/**").hasRole("USER")
                        .requestMatchers("/api/admin/orders/**").hasRole("ADMIN")



                        // 🔐 Everything else needs login
                        .anyRequest().authenticated()
                )

                // ✅ JWT filter
                .addFilterBefore(
                        new JwtAuthFilter(jwtService, userRepo),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
