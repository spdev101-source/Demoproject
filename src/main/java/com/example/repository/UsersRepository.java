package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Users;

public interface UsersRepository extends JpaRepository<Users,Long>{
Optional<Users> findByUsername(String username);
boolean existsByUsername(String username);
}
