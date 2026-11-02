package com.fpt.a2thuantran.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansConfig {

    // BCryptPasswordEncoder : turns a plain text password into a hashed string
    // that is safe to store in the database, and can check a plain password
    // against an existing hash without ever storing the plain password.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
