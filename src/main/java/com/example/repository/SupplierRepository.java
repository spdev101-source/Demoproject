package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsBySupplierEmail(String supplierEmail);
    boolean existsBySupplierEmailAndSupplierIdNot(String supplierEmail, Long supplierId);
    boolean existsBySupplierPhone(String supplierPhone);
    boolean existsBySupplierPhoneAndSupplierIdNot(String supplierPhone, Long supplierId);

    @Query("SELECT s FROM Supplier s WHERE s.supplierName LIKE CONCAT('%', :search, '%')")
    Page<Supplier> searchBySupplierName(@Param("search") String search, Pageable pageable);
}
