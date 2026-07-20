package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{
	
	List<Product> findByBrandBrandId(Long brandId);
	boolean existsByPartNo(String partNo);
	boolean existsByPartNoAndProductIdNot(String partNo,Long productId);
	@Query("select p from Product p")
    Page<Product> getAllProducts(Pageable pageable);
	@Query("select p from Product p where p.productName LIKE :search OR p.partNo Like :search ")
	Page<Product> searchProducts(@Param("search") String search,Pageable pageable);
	/*
	 @Query("SELECT p FROM Product p WHERE p.productName LIKE CONCAT('%', :search, '%') " +
       "OR p.partNo LIKE CONCAT('%', :search, '%')")
Page<Product> searchProducts(@Param("search") String search, Pageable pageable);
	 */
}
