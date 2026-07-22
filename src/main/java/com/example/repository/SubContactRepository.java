package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.SubContact;

public interface SubContactRepository extends JpaRepository<SubContact, Long> {
	// No findByCustomer... query possible — SubContact doesn't know its Customer.
	boolean existsByContactEmail(String contactEmail);
	boolean existsByContactEmailAndSubContactIdNot(String contactEmail, Long subContactId);
}