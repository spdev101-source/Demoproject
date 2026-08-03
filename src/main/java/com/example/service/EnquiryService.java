package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.EnquiryRequestDTO;
import com.example.dto.response.EnquiryResponseDTO;
import com.example.enums.EnquiryStatus;

public interface EnquiryService {
	EnquiryResponseDTO createEnquiry(EnquiryRequestDTO requestDTO);
	Optional<EnquiryResponseDTO> getEnquiryById(Long enquiryId);
	EnquiryResponseDTO updateEnquiry(Long enquiryId, EnquiryRequestDTO requestDTO);
	boolean deleteEnquiry(Long enquiryId);
	Page<EnquiryResponseDTO> getAllEnquiries(int page, int size, String sortBy, String direction);
	Page<EnquiryResponseDTO> searchEnquiries(String search, int page, int size, String sortBy, String direction);
	List<EnquiryResponseDTO> getEnquiriesByCustomerId(Long customerId);
	List<EnquiryResponseDTO> getEnquiriesByStatus(EnquiryStatus status);
	EnquiryResponseDTO closeEnquiry(Long enquiryId);
}