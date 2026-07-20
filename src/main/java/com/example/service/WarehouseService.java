package com.example.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.WarehouseRequestDTO;
import com.example.dto.response.WarehouseResponseDTO;

public interface WarehouseService {

	WarehouseResponseDTO createWarehouse(WarehouseRequestDTO requestDTO);
	Optional<WarehouseResponseDTO> getWarehouseById(Long warehouseId);
	WarehouseResponseDTO updateWarehouse(Long warehouseId,WarehouseRequestDTO requestDTO);
	boolean deleteWarehouse(Long warehouseId);
	Page<WarehouseResponseDTO> getAllWarehouses(int page,int size,String sortBy,String direction);
	Page<WarehouseResponseDTO> searchWarehouses(String search,int page,int size,String sortBy,String direction);

}
