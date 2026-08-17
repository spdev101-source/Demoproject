package com.example.serviceimple;

import java.time.Instant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.configuration.JwtService;
import com.example.dto.request.LoginRequestDTO;
import com.example.dto.request.RefreshTokenRequestDTO;
import com.example.dto.request.RegisterRequestDTO;
import com.example.dto.response.LoginResponseDTO;
import com.example.dto.response.RefreshTokenResponseDTO;
import com.example.entity.RefreshToken;
import com.example.entity.Role;
import com.example.entity.Users;
import com.example.repository.RefreshTokenRepository;
import com.example.repository.RoleRepository;
import com.example.repository.UsersRepository;
import com.example.service.AuthService;

@Service
public class AuthServiceImple implements AuthService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final RefreshTokenRepository refreshTokenRepository;
	private final RoleRepository roleRepository;

	public AuthServiceImple(UsersRepository usersRepository, PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,JwtService jwtService,RefreshTokenRepository refreshTokenRepository,RoleRepository roleRepository) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager=authenticationManager;
		this.jwtService=jwtService;
		this.refreshTokenRepository=refreshTokenRepository;
		this.roleRepository=roleRepository;
	}

	@Override
	public boolean register(RegisterRequestDTO requestDTO) {
		if (usersRepository.existsByUsername(requestDTO.getUsername())) {
			return false;
		}
		Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() ->
                    new RuntimeException("USER role not found"));
		Users user = new Users();
		user.setUsername(requestDTO.getUsername());
		user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
		//user.setRole("USER");
		user.setRole(userRole);
		usersRepository.save(user);
		return true;
	}

	@Override
	@Transactional
	public LoginResponseDTO login(LoginRequestDTO requestDTO) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));

	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//		String username = authentication.getName();

	    String role = authentication.getAuthorities().stream()
	            .map(GrantedAuthority::getAuthority)
	            .filter(a -> a.startsWith("ROLE_"))
	            .findFirst()
	            .map(a -> a.replace("ROLE_", ""))
	            .orElseThrow(() -> new RuntimeException("No role found for user"));
	    String token =jwtService.generateToken(userDetails.getUsername(), role);
		String refreshTokenStr = jwtService.generateRefreshToken(userDetails.getUsername());

		// remove any old refresh token for this user first, so each login invalidates the previous session
				refreshTokenRepository.deleteByUsername(userDetails.getUsername());

				RefreshToken refreshToken = new RefreshToken();
				refreshToken.setToken(refreshTokenStr);
				refreshToken.setUsername(userDetails.getUsername());
				refreshToken.setExpiryDate(Instant.now().plusMillis(jwtService.getRefreshExpirationTime()));
				refreshTokenRepository.save(refreshToken);

		return new LoginResponseDTO(userDetails.getUsername(), role, "Login successful",token,refreshTokenStr);
	}

	@Override
	@Transactional
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

		String newAccessToken = jwtService.generateToken(user.getUsername(), user.getRole().getName());

		return new RefreshTokenResponseDTO(newAccessToken, submittedToken);
	}
}
