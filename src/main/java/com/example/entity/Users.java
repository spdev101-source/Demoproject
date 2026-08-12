package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="appuser")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String role;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	/*
	 package com.example.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 512)
	private String token;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private Instant expiryDate;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public Instant getExpiryDate() { return expiryDate; }
	public void setExpiryDate(Instant expiryDate) { this.expiryDate = expiryDate; }
}
jwt.secret=this-is-a-very-long-secret-key-for-jwt-signing-1234
jwt.expiration=900000
jwt.refresh-expiration=604800000
package com.example.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secretKeyString;

	@Value("${jwt.expiration}")
	private long expirationTime;

	@Value("${jwt.refresh-expiration}")
	private long refreshExpirationTime;

	private SecretKey secretKey;

	@PostConstruct
	public void init() {
		this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
	}

	public String generateToken(String username, String role) {
		return Jwts.builder()
				.subject(username)
				.claim("role", role)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(secretKey)
				.compact();
	}

	public String generateRefreshToken(String username) {
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
				.signWith(secretKey)
				.compact();
	}

	public long getRefreshExpirationTime() {
		return refreshExpirationTime;
	}

	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

	public String extractRole(String token) {
		return getClaims(token).get("role", String.class);
	}

	public boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}

	public boolean isTokenValid(String token) {
		try {
			return !isTokenExpired(token);
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
package com.example.dto.response;

public class LoginResponseDTO {
	private Long id;
	private String token;
	private String refreshToken;
	private String username;
	private String role;
	private String message;

	public LoginResponseDTO(Long id, String token, String refreshToken, String username, String role, String message) {
		this.id = id;
		this.token = token;
		this.refreshToken = refreshToken;
		this.username = username;
		this.role = role;
		this.message = message;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	public String getRefreshToken() { return refreshToken; }
	public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }
}
package com.example.dto.request;

public class RefreshTokenRequestDTO {
	private String refreshToken;

	public String getRefreshToken() { return refreshToken; }
	public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
package com.example.dto.response;

public class RefreshTokenResponseDTO {
	private String token;
	private String refreshToken;

	public RefreshTokenResponseDTO(String token, String refreshToken) {
		this.token = token;
		this.refreshToken = refreshToken;
	}

	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }
	public String getRefreshToken() { return refreshToken; }
	public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
package com.example.service;

import com.example.dto.request.LoginRequestDTO;
import com.example.dto.request.RefreshTokenRequestDTO;
import com.example.dto.request.RegisterRequestDTO;
import com.example.dto.response.LoginResponseDTO;
import com.example.dto.response.RefreshTokenResponseDTO;

public interface AuthService {
	boolean register(RegisterRequestDTO requestDTO);
	LoginResponseDTO login(LoginRequestDTO requestDTO);
	RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO requestDTO);
}
package com.example.serviceimple;

import com.example.configuration.JwtService;
import com.example.dto.request.LoginRequestDTO;
import com.example.dto.request.RefreshTokenRequestDTO;
import com.example.dto.request.RegisterRequestDTO;
import com.example.dto.response.LoginResponseDTO;
import com.example.dto.response.RefreshTokenResponseDTO;
import com.example.entity.RefreshToken;
import com.example.entity.Users;
import com.example.repository.RefreshTokenRepository;
import com.example.repository.UsersRepository;
import com.example.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthServiceImple implements AuthService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final RefreshTokenRepository refreshTokenRepository;

	public AuthServiceImple(UsersRepository usersRepository, PasswordEncoder passwordEncoder,
			JwtService jwtService, AuthenticationManager authenticationManager,
			RefreshTokenRepository refreshTokenRepository) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	@Override
	public boolean register(RegisterRequestDTO requestDTO) {
		if (usersRepository.existsByUsername(requestDTO.getUsername())) {
			return false;
		}
		Users user = new Users();
		user.setUsername(requestDTO.getUsername());
		user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
		user.setRole("USER");
		usersRepository.save(user);
		return true;
	}

	@Override
	public LoginResponseDTO login(LoginRequestDTO requestDTO) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));

		String username = authentication.getName();

		String role = authentication.getAuthorities().stream()
				.findFirst()
				.map(GrantedAuthority::getAuthority)
				.orElseThrow(() -> new RuntimeException("No role found for user"))
				.replace("ROLE_", "");

		Users user = usersRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Invalid username or password"));

		String accessToken = jwtService.generateToken(username, role);
		String refreshTokenStr = jwtService.generateRefreshToken(username);

		// remove any old refresh token for this user first, so each login invalidates the previous session
		refreshTokenRepository.deleteByUsername(username);

		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(refreshTokenStr);
		refreshToken.setUsername(username);
		refreshToken.setExpiryDate(Instant.now().plusMillis(jwtService.getRefreshExpirationTime()));
		refreshTokenRepository.save(refreshToken);

		return new LoginResponseDTO(user.getId(), accessToken, refreshTokenStr, username, role, "Login successful");
	}

	@Override
	public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO requestDTO) {
		String submittedToken = requestDTO.getRefreshToken();

		RefreshToken storedToken = refreshTokenRepository.findByToken(submittedToken)
				.orElseThrow(() -> new RuntimeException("Invalid refresh token"));

		if (storedToken.getExpiryDate().isBefore(Instant.now())) {
			refreshTokenRepository.delete(storedToken);
			throw new RuntimeException("Refresh token expired, please login again");
		}

		Users user = usersRepository.findByUsername(storedToken.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		String newAccessToken = jwtService.generateToken(user.getUsername(), user.getRole());

		return new RefreshTokenResponseDTO(newAccessToken, submittedToken);
	}
}
package com.example.controller;

import com.example.dto.request.LoginRequestDTO;
import com.example.dto.request.RefreshTokenRequestDTO;
import com.example.dto.request.RegisterRequestDTO;
import com.example.dto.response.LoginResponseDTO;
import com.example.dto.response.RefreshTokenResponseDTO;
import com.example.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequestDTO requestDTO) {
		boolean registered = authService.register(requestDTO);
		if (!registered) {
			return new ResponseEntity<>("Username already taken:" + requestDTO.getUsername(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("User registered Successfully", HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
		LoginResponseDTO response = authService.login(requestDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/refresh")
	public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO requestDTO) {
		RefreshTokenResponseDTO response = authService.refreshToken(requestDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
	 */
	
}
