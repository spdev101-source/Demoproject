package com.example.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http
		.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(auth->auth
				.requestMatchers("/api/auth/**","/api/brands/getAll","/api/products/getAll","/api/warehouses/getAll")
				.permitAll()
				.requestMatchers(HttpMethod.GET).hasAnyRole("USER","ADMIN")
				.requestMatchers(HttpMethod.POST).hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
				.anyRequest().authenticated())
		.httpBasic(basic->{});
		return http.build();
	}
}
