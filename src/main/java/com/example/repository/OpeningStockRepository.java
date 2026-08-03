package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.OpeningStock;

public interface OpeningStockRepository extends JpaRepository<OpeningStock, Long> {
	boolean existsByProductProductIdAndWarehouseWarehouseId(Long productId, Long warehouseId);
	boolean existsByProductProductIdAndWarehouseWarehouseIdAndOpeningStockIdNot(Long productId, Long warehouseId, Long openingStockId);

	@Query("SELECT o FROM OpeningStock o WHERE o.product.productName LIKE CONCAT('%', :search, '%') " +
	       "OR o.warehouse.warehouseName LIKE CONCAT('%', :search, '%')")
	Page<OpeningStock> searchOpeningStocks(@Param("search") String search, Pageable pageable);
}