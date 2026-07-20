package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.request.WarehouseRequestDTO;
import com.example.dto.response.WarehouseResponseDTO;
import com.example.service.WarehouseService;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

	private final WarehouseService warehouseService;

	public WarehouseController(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}

	@PostMapping("/save")
	public ResponseEntity<WarehouseResponseDTO> createWarehouse(@RequestBody WarehouseRequestDTO requestDTO) {
		WarehouseResponseDTO saved = warehouseService.createWarehouse(requestDTO);
		return new ResponseEntity<WarehouseResponseDTO>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{warehouseId}")
	public ResponseEntity<WarehouseResponseDTO> getWarehouseById(@PathVariable Long warehouseId) {
		WarehouseResponseDTO warehouse = warehouseService.getWarehouseById(warehouseId)
				.orElseThrow(() -> new RuntimeException("Warehouse not found with id:"+warehouseId));
		return new ResponseEntity<WarehouseResponseDTO>(warehouse, HttpStatus.OK);

	}
	@PutMapping("/update/{warehouseId}")
	public WarehouseResponseDTO updateWarehouse(@PathVariable Long warehouseId,@RequestBody WarehouseRequestDTO requestDTO)
	{
		return warehouseService.updateWarehouse(warehouseId, requestDTO);
	}
	@DeleteMapping("/delete/{warehouseId}")
	public ResponseEntity<String> deleteWarehouse(@PathVariable Long warehouseId)
	{
		boolean deleted=warehouseService.deleteWarehouse(warehouseId);
		if(!deleted)
		{
			return new  ResponseEntity<String>("warehouse not found with id:"+warehouseId,HttpStatus.NOT_FOUND);
		}
		return new  ResponseEntity<String>("warehouse deleted Successfully",HttpStatus.OK);

	}
	@GetMapping("/getAll")
	public Page<WarehouseResponseDTO> getAllWarehouses(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction)
	{
		return warehouseService.getAllWarehouses(page, size, sortBy, direction);
	}
	@GetMapping("/searching")
	public Page<WarehouseResponseDTO> searchWarehouses(
			@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction)
	{
		return warehouseService.searchWarehouses(search, page, size, sortBy, direction);
	}
}
