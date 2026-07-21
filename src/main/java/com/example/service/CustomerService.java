package com.example.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.CustomerRequestDTO;
import com.example.dto.response.CustomerResponseDTO;

public interface CustomerService {
    CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO);
    Optional<CustomerResponseDTO> getCustomerById(Long customerId);
    CustomerResponseDTO updateCustomer(Long customerId, CustomerRequestDTO requestDTO);
    boolean deleteCustomer(Long customerId);
//    CustomerResponseDTO addCustomerWithContacts(CustomerRequestDTO request);
    Page<CustomerResponseDTO> getAllCustomers(int page, int size, String sortBy, String direction);
    Page<CustomerResponseDTO> searchCustomer(String search, int page, int size, String sortBy, String direction);
}
