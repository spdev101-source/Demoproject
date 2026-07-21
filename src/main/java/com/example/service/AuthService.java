package com.example.service;

import com.example.dto.request.LoginRequestDTO;
import com.example.dto.request.RegisterRequestDTO;
import com.example.dto.response.LoginResponseDTO;

public interface AuthService {
    String registerUser(RegisterRequestDTO requestDTO);
    LoginResponseDTO loginUser(LoginRequestDTO requestDTO);
}