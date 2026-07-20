package com.example.serviceimple;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.request.BrandRequestDTO;
import com.example.dto.response.BrandResponseDTO;
import com.example.entity.Brand;
import com.example.repository.BrandRepository;
import com.example.repository.ProductRepository;
import com.example.service.BrandService;

@Service
public class BrandServiceImple implements BrandService {

	private final BrandRepository brandRepository;
	private final ModelMapper modelMapper;
	private final ProductRepository productRepository;
	public BrandServiceImple(BrandRepository brandRepository, ModelMapper modelMapper,ProductRepository productRepository) {
		this.brandRepository = brandRepository;
		this.modelMapper = modelMapper;
		this.productRepository=productRepository;
	}

	@Override
	public BrandResponseDTO createBrand(BrandRequestDTO requestDTO) {
		if (brandRepository.existsByBrandName(requestDTO.getBrandName())) {
			throw new RuntimeException("Brand Already exists with Name:" + requestDTO.getBrandName());
		}
		Brand brand = modelMapper.map(requestDTO, Brand.class);
		Brand saved = brandRepository.save(brand);
		return modelMapper.map(saved, BrandResponseDTO.class);
	}

	@Override
	public Optional<BrandResponseDTO> getBrandById(Long brandId) {
		return brandRepository.findById(brandId).map(brand -> modelMapper.map(brand, BrandResponseDTO.class));
	}

	@Override
	public BrandResponseDTO updateBrand(Long brandId, BrandRequestDTO requestDTO) {
		Brand brand = brandRepository.findById(brandId)
				.orElseThrow(() -> new RuntimeException("Brand not found with id:" + brandId));
		if (brandRepository.existsByBrandNameAndBrandIdNot(requestDTO.getBrandName(), brandId)) {
			throw new RuntimeException("Brand Already exists with Name:" + requestDTO.getBrandName());
		}
		brand.setBrandName(requestDTO.getBrandName());
		Brand updated = brandRepository.save(brand);
		return modelMapper.map(updated, BrandResponseDTO.class);
	}

	
	@Override
	public boolean deleteBrand(Long brandId) {

		if (brandRepository.existsById(brandId)) {
			return false;
		}
		if(!productRepository.findByBrandBrandId(brandId).isEmpty())
		{
			return false;
		}
		brandRepository.deleteById(brandId);
		return true;
	}
	


@Override
public Page<BrandResponseDTO> getAllBrands(int page, int size, String sortBy, String direction) {
	Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
	Pageable pageable = PageRequest.of(page, size, sort);
	return brandRepository.findAll(pageable).map(brand -> modelMapper.map(brand, BrandResponseDTO.class));
}

@Override
public Page<BrandResponseDTO> searchBrand(String search, int page, int size, String sortBy, String direction) {
	Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
	Pageable pageable = PageRequest.of(page, size, sort);
	return brandRepository.searchByBrandName(search, pageable).map(brand -> modelMapper.map(brand, BrandResponseDTO.class));
}

}
