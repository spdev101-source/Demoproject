package com.example.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer,Long>{
boolean existsByCustomerEmail(String customerEmail);
boolean existsByCustomerPhone(String customerPhone);
boolean existsByCustomerNameAndCustomerIdNot(String customerName,Long customerId);
boolean existsByCustomerPhoneAndCustomerIdNot(String customerPhone,Long customerId);
@Query("select c from Customer c")
Page<Customer> getAllCustomers(Pageable pageable);
@Query("SELECT c FROM Customer c WHERE c.customerName LIKE CONCAT('%', :search, '%')")
Page<Customer> searchByCustomerName(@Param("search") String search, Pageable pageable);
}
