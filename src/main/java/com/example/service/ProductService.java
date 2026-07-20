package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.ProductRequestDTO;
import com.example.dto.response.ProductResponseDTO;

public interface ProductService {

	ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
	Optional<ProductResponseDTO> getProductById(Long productId);
	ProductResponseDTO updateProduct(Long productId,ProductRequestDTO requestDTO);
	boolean deleteProduct(Long productId);
	List<ProductResponseDTO> getProductsByBrandId(Long brandId);
	Page<ProductResponseDTO> getAllProducts(int page,int size,String sortBy,String direction);
	Page<ProductResponseDTO> searchProducts(String search,int page,int size,String sortBy,String direction);

}
