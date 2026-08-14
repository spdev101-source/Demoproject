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