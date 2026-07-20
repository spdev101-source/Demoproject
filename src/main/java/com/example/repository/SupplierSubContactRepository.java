package com.example.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.SupplierSubContact;

public interface SupplierSubContactRepository extends JpaRepository<SupplierSubContact, Long> {
    List<SupplierSubContact> findBySupplierSupplierId(Long supplierId);
    boolean existsByContactEmailAndSupplierSupplierId(String contactEmail, Long supplierId);
    boolean existsByContactEmailAndSupplierSupplierIdAndSubContactIdNot(String contactEmail, Long supplierId, Long subContactId);
}

