package com.example.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Warehouse;
import com.example.enums.WarehouseType;

public interface WarehouseRepository extends JpaRepository<Warehouse,Long>{

	@Query("select w from Warehouse w")
	Page<Warehouse> getAllWarehouses(Pageable pageable);
	boolean existsByWarehouseName(String warehouseName);
	boolean existsByType(WarehouseType type);
	boolean existsByWarehouseNameAndWarehouseIdNot(String warehouseName,Long warehouseId);
	boolean existsByTypeAndWarehouseIdNot(WarehouseType type,Long warehouseId);
	@Query("SELECT w FROM Warehouse w WHERE w.warehouseName LIKE :search " +
		       "OR w.location LIKE :search " +
		       "OR CAST(w.type AS string) LIKE :search")
//	@Query("select w from Warehouse w where w.warehouseName LIKE :search " + "OR w.location LIKE :search " + "OR w.type like :search ")
	Page<Warehouse> searchWarehouses(@Param("search") String search,Pageable pageable);
}
