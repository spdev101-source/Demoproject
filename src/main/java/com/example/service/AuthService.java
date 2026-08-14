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
