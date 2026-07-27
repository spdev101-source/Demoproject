package com.example.dto.response;

public class LoginResponseDTO {

	private String username;
	private String role;
	private String message;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LoginResponseDTO(String username, String role, String message) {
		super();
		this.username = username;
		this.role = role;
		this.message = message;
	}
	
}
