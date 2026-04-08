package com.security_contacts.AppSecurityContacts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //Cadena de filtros
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf( csrf -> csrf.disable())

            .authorizeHttpRequests(auth ->
                    auth
                            .requestMatchers("/contacts/public/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/contacts").hasAnyRole("USER","ADMIN")
                            .requestMatchers(HttpMethod.POST,"/contacts").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/contacts/**").hasRole("ADMIN")
                            .anyRequest()
                            .authenticated())

                .httpBasic(basic -> {});

        return http.build();
    }

    @Bean
    //Creo los dos usuarios en memoria
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin,user);

    }

    //Codificador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
