package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.SubContact;

public interface SubContactRepository extends JpaRepository<SubContact, Long> {
    List<SubContact> findByCustomerCustomerId(Long customerId);
    boolean existsByContactEmailAndCustomerCustomerId(String contactEmail, Long customerId);
    boolean existsByContactEmailAndCustomerCustomerIdAndSubContactIdNot(String contactEmail, Long customerId, Long subContactId);
}
