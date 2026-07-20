package com.example.controller;

import java.util.List;

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

import com.example.dto.request.ProductRequestDTO;
import com.example.dto.response.ProductResponseDTO;
import com.example.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/save")
	public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO requestDTO) {
		ProductResponseDTO saved = productService.createProduct(requestDTO);
		return new ResponseEntity<ProductResponseDTO>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{productId}")
	public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId) {
		ProductResponseDTO product = productService.getProductById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found with id:" + productId));
		return new ResponseEntity<ProductResponseDTO>(product, HttpStatus.OK);
	}

	@PutMapping("/update/{productId}")
	public ProductResponseDTO updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDTO requestDTO) {
		return productService.updateProduct(productId, requestDTO);
	}

	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
		boolean deleted = productService.deleteProduct(productId);
		if (!deleted) {
			return new ResponseEntity<String>("product not  found with id:" + productId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("product deleted Successfully", HttpStatus.OK);

	}

	@GetMapping("/brand/{brandId}")
	public List<ProductResponseDTO> getProductsByBrandId(@PathVariable Long brandId) {
		return productService.getProductsByBrandId(brandId);
	}

	@GetMapping("/getAll")
	public Page<ProductResponseDTO> getAllProducts(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return productService.getAllProducts(page, size, sortBy, direction);
	}
	@GetMapping("/searching")
	public Page<ProductResponseDTO> searchProducts(
			@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return productService.searchProducts(search, page, size, sortBy, direction);
	}
}
