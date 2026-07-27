package com.example.serviceimple;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	public AuthServiceImple(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
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
		Users user = usersRepository.findByUsername(requestDTO.getUsername())
				.orElseThrow(() -> new RuntimeException("Invalid username and password"));
		if(!passwordEncoder.matches(requestDTO.getPassword(),user.getPassword()))
		{
			throw new RuntimeException("Invalid username and password");
		}
		
		return new LoginResponseDTO(requestDTO.getUsername(),user.getRole(),"Login Successfully");
	}

}
