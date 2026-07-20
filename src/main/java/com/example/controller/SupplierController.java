package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.SupplierRequestDTO;
import com.example.dto.response.SupplierResponseDTO;
import com.example.service.SupplierService;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping("/save")
    public ResponseEntity<SupplierResponseDTO> createSupplier(@RequestBody SupplierRequestDTO requestDTO) {
        SupplierResponseDTO saved = supplierService.createSupplier(requestDTO);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/get/{supplierId}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable Long supplierId) {
        SupplierResponseDTO supplier = supplierService.getSupplierById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier Not found with id:" + supplierId));
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @PutMapping("/update/{supplierId}")
    public SupplierResponseDTO updateSupplier(@PathVariable Long supplierId, @RequestBody SupplierRequestDTO requestDTO) {
        return supplierService.updateSupplier(supplierId, requestDTO);
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long supplierId) {
        boolean deleted = supplierService.deleteSupplier(supplierId);
        if (!deleted) {
            return new ResponseEntity<>("Supplier not found with id:" + supplierId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Supplier deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public Page<SupplierResponseDTO> getAllSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "supplierId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return supplierService.getAllSuppliers(page, size, sortBy, direction);
    }

    @GetMapping("/searching")
    public Page<SupplierResponseDTO> searchSupplier(
            @RequestParam String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "supplierId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return supplierService.searchSupplier(search, page, size, sortBy, direction);
    }
}
