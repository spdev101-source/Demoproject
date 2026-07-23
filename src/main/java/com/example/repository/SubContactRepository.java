package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.SubContact;

public interface SubContactRepository extends JpaRepository<SubContact, Long> {
}