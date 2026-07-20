package com.example.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.SupplierRequestDTO;
import com.example.dto.response.SupplierResponseDTO;

public interface SupplierService {
    SupplierResponseDTO createSupplier(SupplierRequestDTO requestDTO);
    Optional<SupplierResponseDTO> getSupplierById(Long supplierId);
    SupplierResponseDTO updateSupplier(Long supplierId, SupplierRequestDTO requestDTO);
    boolean deleteSupplier(Long supplierId);
    Page<SupplierResponseDTO> getAllSuppliers(int page, int size, String sortBy, String direction);
    Page<SupplierResponseDTO> searchSupplier(String search, int page, int size, String sortBy, String direction);
}
