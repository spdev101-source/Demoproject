package com.example.service;

import java.util.List;
import java.util.Optional;
import com.example.dto.request.SubContactRequestDTO;
import com.example.dto.response.SubContactResponseDTO;

public interface SubContactService {
	SubContactResponseDTO createSubContact(SubContactRequestDTO requestDTO);
	Optional<SubContactResponseDTO> getSubContactById(Long subContactId);
	SubContactResponseDTO updateSubContact(Long subContactId, SubContactRequestDTO requestDTO);
	boolean deleteSubContact(Long subContactId);
	List<SubContactResponseDTO> getSubContactsByCustomerId(Long customerId);
}