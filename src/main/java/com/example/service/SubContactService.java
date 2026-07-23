package com.example.service;

import java.util.Optional;

import com.example.dto.request.SubContactRequestDTO;
import com.example.dto.response.SubContactResponseDTO;

public interface SubContactService {
	Optional<SubContactResponseDTO> getSubContactById(Long subContactId);
	SubContactResponseDTO updateSubContact(Long subContactId, SubContactRequestDTO requestDTO);
	boolean deleteSubContact(Long subContactId);
}