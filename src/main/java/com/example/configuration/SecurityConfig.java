package com.example.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

                .requestMatchers(HttpMethod.GET,
                        "/api/customers/**", "/api/subcontacts/**")
                    .hasAnyRole("ADMIN", "USER")

                .requestMatchers(HttpMethod.POST,
                        "/api/customers/**", "/api/subcontacts/**")
                    .hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT,
                        "/api/customers/**", "/api/subcontacts/**")
                    .hasAnyRole("ADMIN", "USER")

                .requestMatchers(HttpMethod.DELETE,
                        "/api/customers/**", "/api/subcontacts/**")
                    .hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .httpBasic(basic -> {})
            .authenticationProvider(authenticationProvider());
        return http.build();
    }
}
