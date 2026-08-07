package com.example.serviceimple;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.configuration.JwtService;
import com.example.dto.request.LoginRequestDTO;
import com.example.dto.request.RegisterRequestDTO;
import com.example.dto.response.LoginResponseDTO;
import com.example.entity.Users;
import com.example.repository.UsersRepository;
import com.example.service.AuthService;

@Service
public class AuthServiceImple implements AuthService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthServiceImple(UsersRepository usersRepository, PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,JwtService jwtService) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager=authenticationManager;
		this.jwtService=jwtService;
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

	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//		String username = authentication.getName();

		String role=authentication.getAuthorities().toString().replace("ROLE_", "");
		String token =jwtService.generateToken(userDetails.getUsername(), role);

		return new LoginResponseDTO(userDetails.getUsername(), role, "Login successful",token);
	}
}
