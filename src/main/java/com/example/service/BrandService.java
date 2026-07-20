package com.example.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.BrandRequestDTO;
import com.example.dto.response.BrandResponseDTO;

public interface BrandService {

	BrandResponseDTO createBrand(BrandRequestDTO requestDTO);
	Optional<BrandResponseDTO> getBrandById(Long brandId);
	BrandResponseDTO updateBrand(Long brandId,BrandRequestDTO requestDTO);
	boolean deleteBrand(Long brandId);
	Page<BrandResponseDTO> getAllBrands(int page,int size,String sortBy,String direction);
	Page<BrandResponseDTO> searchBrand(String search,int page,int size,String sortBy,String direction);
}
