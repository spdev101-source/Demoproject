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

import com.example.dto.request.BrandRequestDTO;
import com.example.dto.response.BrandResponseDTO;
import com.example.service.BrandService;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

	private final BrandService brandService;

	public BrandController(BrandService brandService) {
		this.brandService = brandService;
	}

	@PostMapping("/save")
	public ResponseEntity<BrandResponseDTO> createBrand(@RequestBody BrandRequestDTO requestDTO) {
		BrandResponseDTO saved = brandService.createBrand(requestDTO);
		return new ResponseEntity<BrandResponseDTO>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{brandId}")
	public ResponseEntity<BrandResponseDTO> getBrandById(@PathVariable Long brandId) {
		BrandResponseDTO brand = brandService.getBrandById(brandId)
				.orElseThrow(() -> new RuntimeException("Brand Not found with id:" + brandId));
		return new ResponseEntity<BrandResponseDTO>(brand, HttpStatus.OK);
	}

	@PutMapping("/update/{brandId}")
	public BrandResponseDTO updateBrand(@PathVariable Long brandId, @RequestBody BrandRequestDTO requestDTO) {
		return brandService.updateBrand(brandId, requestDTO);
	}

	@DeleteMapping("/{brandId}")
	public ResponseEntity<String> deleteBrand(@PathVariable Long brandId) {
		boolean deleted = brandService.deleteBrand(brandId);
		if (!deleted) {
			return new ResponseEntity<String>("Brand not found with id:" + brandId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Brand deleted Successfully", HttpStatus.OK);

	}

	@GetMapping("/getAll")
	public Page<BrandResponseDTO> getAllBrands(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, 
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return brandService.getAllBrands(page, size, sortBy, direction);
	}
	@GetMapping("/searching")
	public Page<BrandResponseDTO> searchBrand(
			@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, 
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return brandService.searchBrand(search, page, size, sortBy, direction);
	}
}
