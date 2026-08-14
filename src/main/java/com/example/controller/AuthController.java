package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.request.LoginRequestDTO;
import com.example.dto.request.RefreshTokenRequestDTO;
import com.example.dto.request.RegisterRequestDTO;
import com.example.dto.response.LoginResponseDTO;
import com.example.dto.response.RefreshTokenResponseDTO;
import com.example.service.AuthService;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	public AuthController(AuthService authService)
	{
		this.authService=authService;
	}
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequestDTO requestDTO)
	{
		boolean register=authService.register(requestDTO);
		if(!register)
		{
			return new ResponseEntity<String>("User Already exists with name:"+requestDTO.getUsername(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("User Registered Successfully",HttpStatus.CREATED);
	}
	@PostMapping("/login")
	public LoginResponseDTO login(@RequestBody LoginRequestDTO requestDTO)
	{
		return authService.login(requestDTO);
	}
	@PostMapping("/refresh")
	public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO requestDTO) {
		RefreshTokenResponseDTO response = authService.refreshToken(requestDTO);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
