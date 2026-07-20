package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.SupplierSubContactRequestDTO;
import com.example.dto.response.SupplierSubContactResponseDTO;
import com.example.service.SupplierSubContactService;

@RestController
@RequestMapping("/api/supplier-subcontacts")
public class SupplierSubContactController {

    private final SupplierSubContactService supplierSubContactService;

    public SupplierSubContactController(SupplierSubContactService supplierSubContactService) {
        this.supplierSubContactService = supplierSubContactService;
    }

    @PostMapping("/save")
    public ResponseEntity<SupplierSubContactResponseDTO> createSubContact(@RequestBody SupplierSubContactRequestDTO requestDTO) {
        SupplierSubContactResponseDTO saved = supplierSubContactService.createSubContact(requestDTO);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/get/{subContactId}")
    public ResponseEntity<SupplierSubContactResponseDTO> getSubContactById(@PathVariable Long subContactId) {
        SupplierSubContactResponseDTO subContact = supplierSubContactService.getSubContactById(subContactId)
                .orElseThrow(() -> new RuntimeException("Sub-contact Not found with id:" + subContactId));
        return new ResponseEntity<>(subContact, HttpStatus.OK);
    }

    @PutMapping("/update/{subContactId}")
    public SupplierSubContactResponseDTO updateSubContact(@PathVariable Long subContactId, @RequestBody SupplierSubContactRequestDTO requestDTO) {
        return supplierSubContactService.updateSubContact(subContactId, requestDTO);
    }

    @DeleteMapping("/{subContactId}")
    public ResponseEntity<String> deleteSubContact(@PathVariable Long subContactId) {
        boolean deleted = supplierSubContactService.deleteSubContact(subContactId);
        if (!deleted) {
            return new ResponseEntity<>("Sub-contact not found with id:" + subContactId, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Sub-contact deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<SupplierSubContactResponseDTO> getSubContactsBySupplierId(@PathVariable Long supplierId) {
        return supplierSubContactService.getSubContactsBySupplierId(supplierId);
    }
}
