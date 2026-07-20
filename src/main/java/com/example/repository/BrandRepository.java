package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand,Long>{
	boolean existsByBrandName(String brandName);
	boolean existsByBrandNameAndBrandIdNot(String brandName,Long brandId);
	@Query("select b from Brand b")
	Page<Brand> getAllBrands(Pageable pageable);
	@Query("SELECT b FROM Brand b WHERE LOWER(b.brandName) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<Brand> searchByBrandName(String search,Pageable pageable);

}
