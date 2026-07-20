package com.example.serviceimple;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.request.ProductRequestDTO;
import com.example.dto.response.ProductResponseDTO;
import com.example.entity.Brand;
import com.example.entity.Product;
import com.example.repository.BrandRepository;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;

@Service
public class ProductServiceImple implements ProductService {

	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;
	private final BrandRepository brandRepository;

	public ProductServiceImple(ProductRepository productRepository, ModelMapper modelMapper,
			BrandRepository brandRepository) {
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
		this.brandRepository = brandRepository;
	}

	@Override
	public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
		Brand brand = brandRepository.findById(requestDTO.getBrandId())
				.orElseThrow(() -> new RuntimeException("Brand Not found with id:" + requestDTO.getBrandId()));
		if (productRepository.existsByPartNo(requestDTO.getPartNo())) {
			throw new RuntimeException("Product Already exists with partNo:" + requestDTO.getPartNo());
		}
		/*
		 Product product = new Product();
		 
    product.setProductName(requestDTO.getProductName());
    product.setPartNo(requestDTO.getPartNo());
    product.setBrand(brand);
    Product saved = productRepository.save(product);
    return modelMapper.map(saved, ProductResponseDTO.class);
		 */
		Product product = new Product();
		product.setProductName(requestDTO.getProductName());
		product.setPartNo(requestDTO.getPartNo());
		product.setBrand(brand);
		Product saved = productRepository.save(product);
		ProductResponseDTO dto = modelMapper.map(saved, ProductResponseDTO.class);
		dto.setBrandName(saved.getBrand().getBrandName());
		return dto;
	}

	@Override
	public Optional<ProductResponseDTO> getProductById(Long productId) {

		return productRepository.findById(productId).map(product -> modelMapper.map(product, ProductResponseDTO.class));

	}

	@Override
	public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO requestDTO) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product Not found with id:" + productId));

		Brand brand = brandRepository.findById(requestDTO.getBrandId())
				.orElseThrow(() -> new RuntimeException("Brand not found with id:" + requestDTO.getBrandId()));
		if (productRepository.existsByPartNoAndProductIdNot(requestDTO.getPartNo(), productId)) {
			throw new RuntimeException("Product Already exists with partNo:" + requestDTO.getPartNo());
		}
		product.setProductName(requestDTO.getProductName());
		product.setPartNo(requestDTO.getPartNo());
		product.setBrand(brand);
		Product updated = productRepository.save(product);
		ProductResponseDTO dto = modelMapper.map(updated, ProductResponseDTO.class);
		dto.setBrandName(updated.getBrand().getBrandName());
		return dto;
	}

	@Override
	public boolean deleteProduct(Long productId) {
		if (productRepository.existsById(productId)) {
			productRepository.deleteById(productId);
			return true;
		}
		return false;
	}

	@Override
	public List<ProductResponseDTO> getProductsByBrandId(Long brandId) {
		return productRepository.findByBrandBrandId(brandId).stream()
				.map(product -> modelMapper.map(product, ProductResponseDTO.class)).toList();
	}

	@Override
	public Page<ProductResponseDTO> getAllProducts(int page, int size, String sortBy, String direction) {
		Sort sort=direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageable=PageRequest.of(page, size,sort);
		return productRepository.findAll(pageable).map(product->modelMapper.map(product,ProductResponseDTO.class));
	}

	@Override
	public Page<ProductResponseDTO> searchProducts(String search, int page, int size, String sortBy, String direction) {
		Sort sort=direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageable=PageRequest.of(page, size,sort);
		return productRepository.searchProducts(search, pageable).map(product->modelMapper.map(product,ProductResponseDTO.class));
	}

}
