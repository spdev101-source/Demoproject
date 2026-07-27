package com.example.service;

import com.example.dto.request.LoginRequestDTO;
import com.example.dto.request.RegisterRequestDTO;
import com.example.dto.response.LoginResponseDTO;

public interface AuthService {
boolean register(RegisterRequestDTO requestDTO);
LoginResponseDTO login(LoginRequestDTO requestDTO);
}
