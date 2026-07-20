package com.example.serviceimple;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.request.WarehouseRequestDTO;
import com.example.dto.response.WarehouseResponseDTO;
import com.example.entity.Warehouse;
import com.example.enums.WarehouseType;
import com.example.repository.WarehouseRepository;
import com.example.service.WarehouseService;

@Service
public class WarehouseServiceImple implements WarehouseService {

	private final WarehouseRepository warehouseRepository;
	private final ModelMapper modelMapper;

	public WarehouseServiceImple(WarehouseRepository warehouseRepository, ModelMapper modelMapper) {
		this.warehouseRepository = warehouseRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public WarehouseResponseDTO createWarehouse(WarehouseRequestDTO requestDTO) {
		if(warehouseRepository.existsByWarehouseName(requestDTO.getWarehouseName()))
		{
			throw new RuntimeException("Warehouse Already exists with Name:"+requestDTO.getWarehouseName());
		}
		if(requestDTO.getType()==WarehouseType.PRIMARY&&warehouseRepository.existsByType(WarehouseType.PRIMARY))
		{
			throw new RuntimeException("Primary warehouse type Already exists");
		}
		Warehouse warehouse = modelMapper.map(requestDTO, Warehouse.class);
		Warehouse saved = warehouseRepository.save(warehouse);
		return modelMapper.map(saved, WarehouseResponseDTO.class);
	}

	@Override
	public Optional<WarehouseResponseDTO> getWarehouseById(Long warehouseId) {
		return warehouseRepository.findById(warehouseId)
				.map(warehouse -> modelMapper.map(warehouse, WarehouseResponseDTO.class));
	}

	@Override
	public WarehouseResponseDTO updateWarehouse(Long warehouseId, WarehouseRequestDTO requestDTO) {
		Warehouse warehouse = warehouseRepository.findById(warehouseId)
				.orElseThrow(() -> new RuntimeException("Warehouse not found with id:" + warehouseId));
		if(warehouseRepository.existsByWarehouseNameAndWarehouseIdNot(requestDTO.getWarehouseName(), warehouseId))
		{
			throw new RuntimeException("Warehouse Already exists with Name:"+requestDTO.getWarehouseName());

		}
		if(requestDTO.getType()==WarehouseType.PRIMARY&&warehouseRepository.existsByTypeAndWarehouseIdNot(WarehouseType.PRIMARY, warehouseId))
		{
			throw new RuntimeException("Primary warehouse type Already exists");
		}
		warehouse.setWarehouseName(requestDTO.getWarehouseName());
		warehouse.setLocation(requestDTO.getLocation());
		warehouse.setType(requestDTO.getType());
		Warehouse updated = warehouseRepository.save(warehouse);
		return modelMapper.map(updated, WarehouseResponseDTO.class);
	}

	@Override
	public boolean deleteWarehouse(Long warehouseId) {
		if (warehouseRepository.existsById(warehouseId)) {
			warehouseRepository.deleteById(warehouseId);
			return true;
		}
		return false;
	}

	@Override
	public Page<WarehouseResponseDTO> getAllWarehouses(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return warehouseRepository.findAll(pageable)
				.map(warehouse -> modelMapper.map(warehouse, WarehouseResponseDTO.class));
	}

	@Override
	public Page<WarehouseResponseDTO> searchWarehouses(String search, int page, int size, String sortBy,
			String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return warehouseRepository.searchWarehouses(search,pageable)
				.map(warehouse -> modelMapper.map(warehouse, WarehouseResponseDTO.class));
	}

}
