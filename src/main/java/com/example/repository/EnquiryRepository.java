package com.example.repository;

import com.example.entity.Enquiry;
import com.example.enums.EnquiryStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {
	List<Enquiry> findByCustomerCustomerId(Long customerId);
	List<Enquiry> findByStatus(EnquiryStatus status);

	@Query("SELECT e FROM Enquiry e WHERE e.customer.customerName LIKE CONCAT('%', :search, '%')")
	Page<Enquiry> searchEnquiries(@Param("search") String search, Pageable pageable);
}