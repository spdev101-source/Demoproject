package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.dto.request.SupplierSubContactRequestDTO;
import com.example.dto.response.SupplierSubContactResponseDTO;

public interface SupplierSubContactService {
    SupplierSubContactResponseDTO createSubContact(SupplierSubContactRequestDTO requestDTO);
    Optional<SupplierSubContactResponseDTO> getSubContactById(Long subContactId);
    SupplierSubContactResponseDTO updateSubContact(Long subContactId, SupplierSubContactRequestDTO requestDTO);
    boolean deleteSubContact(Long subContactId);
    List<SupplierSubContactResponseDTO> getSubContactsBySupplierId(Long supplierId);
}
