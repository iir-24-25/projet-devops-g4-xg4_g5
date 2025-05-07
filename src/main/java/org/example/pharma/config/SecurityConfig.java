//package org.example.pharma.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/login", "/static/**").permitAll()
//                        .requestMatchers("/welcome").authenticated() // Protéger la page de bienvenue
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/welcome", true) // Redirection vers la page de bienvenue
//                        .permitAll()
//                )
//                .logout((logout) -> logout
//                        .logoutSuccessUrl("/login")
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//}