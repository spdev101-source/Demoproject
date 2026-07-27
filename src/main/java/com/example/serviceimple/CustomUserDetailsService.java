package com.example.serviceimple;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.Users;
import com.example.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UsersRepository usersRepository;
	public CustomUserDetailsService(UsersRepository usersRepository)
	{
		this.usersRepository=usersRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=usersRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
		return User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRole())
				.build();
	}

}
