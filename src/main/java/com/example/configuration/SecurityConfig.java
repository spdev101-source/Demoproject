package com.example.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.http.HttpMethod;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final CustomUserDetailsService userDetailsService;
//
//    public SecurityConfig(CustomUserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
//
//                .requestMatchers(HttpMethod.GET,
//                        "/api/customers/**", "/api/subcontacts/**")
//                    .hasAnyRole("ADMIN", "USER")
//
//                .requestMatchers(HttpMethod.POST,
//                        "/api/customers/**", "/api/subcontacts/**")
//                    .hasAnyRole("ADMIN", "USER")
//                .requestMatchers(HttpMethod.PUT,
//                        "/api/customers/**", "/api/subcontacts/**")
//                    .hasAnyRole("ADMIN", "USER")
//
//                .requestMatchers(HttpMethod.DELETE,
//                        "/api/customers/**", "/api/subcontacts/**")
//                    .hasRole("ADMIN")
//
//                .anyRequest().authenticated()
//            )
//            .httpBasic(basic -> {})
//            .authenticationProvider(authenticationProvider());
//        return http.build();
//    }
//}

///=======================Step 2========================================
//@Configuration
//public class SecurityConfig {
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(encoder.encode("admin123"))
//				.roles("ADMIN")
//				.build();
//
//		UserDetails user = User.builder()
//				.username("user")
//				.password(encoder.encode("user123"))
//				.roles("USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(admin, user);
//	}
//}
//==========================STEP 3==================================================


@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		UserDetails admin = User.builder().username("admin").password(encoder.encode("admin123")).roles("ADMIN").build();
		UserDetails user = User.builder().username("user").password(encoder.encode("user123")).roles("USER").build();
		return new InMemoryUserDetailsManager(admin, user);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/brands/getAll", "/api/brands/searching", "/api/products/getAll",
						"/api/products/searching", "/api/products/brand/**", "/api/warehouses/getAll",
						"/api/warehouses/searching", "/api/customers/getAll", "/api/customers/searching").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
				.anyRequest().permitAll()
			)
			.httpBasic(basic -> {});
		return http.build();
	}
}
//2. Restart the app.
//
//What to check — this is the important one, test all 6
//
//A. GET /api/brands/getAll — no auth at all → expect 200 (public).
//
//B. GET /api/customers/get/1 — no auth → expect 401 (needs login, none given).
//
//C. Same call, with user/user123 → expect 200 (logged in, allowed to view).
//
//D. POST /api/customers/save with user/user123 → expect 403 — this is the one to pay close attention to. Not 401. Read the next section carefully before testing this one.
//
//E. Same POST, with admin/admin123 → expect 201.
//
//F. DELETE /api/brands/1 with user/user123 → expect 403 as wel