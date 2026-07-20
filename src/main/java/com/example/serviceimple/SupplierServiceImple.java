package com.example.serviceimple;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.request.SupplierRequestDTO;
import com.example.dto.response.SupplierResponseDTO;
import com.example.entity.Supplier;
import com.example.repository.SupplierRepository;
import com.example.service.SupplierService;

@Service
public class SupplierServiceImple implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public SupplierServiceImple(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SupplierResponseDTO createSupplier(SupplierRequestDTO requestDTO) {
        if (supplierRepository.existsBySupplierEmail(requestDTO.getSupplierEmail())) {
            throw new RuntimeException("Supplier Already exists with Email:" + requestDTO.getSupplierEmail());
        }
        if (supplierRepository.existsBySupplierPhone(requestDTO.getSupplierPhone())) {
            throw new RuntimeException("Supplier Already exists with Phone:" + requestDTO.getSupplierPhone());
        }
        Supplier supplier = modelMapper.map(requestDTO, Supplier.class);
        Supplier saved = supplierRepository.save(supplier);
        return modelMapper.map(saved, SupplierResponseDTO.class);
    }

    @Override
    public Optional<SupplierResponseDTO> getSupplierById(Long supplierId) {
        return supplierRepository.findById(supplierId).map(supplier -> modelMapper.map(supplier, SupplierResponseDTO.class));
    }

    @Override
    public SupplierResponseDTO updateSupplier(Long supplierId, SupplierRequestDTO requestDTO) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id:" + supplierId));
        if (supplierRepository.existsBySupplierEmailAndSupplierIdNot(requestDTO.getSupplierEmail(), supplierId)) {
            throw new RuntimeException("Supplier Already exists with Email:" + requestDTO.getSupplierEmail());
        }
        if (supplierRepository.existsBySupplierPhoneAndSupplierIdNot(requestDTO.getSupplierPhone(), supplierId)) {
            throw new RuntimeException("Supplier Already exists with Phone:" + requestDTO.getSupplierPhone());
        }
        supplier.setSupplierName(requestDTO.getSupplierName());
        supplier.setSupplierPhone(requestDTO.getSupplierPhone());
        supplier.setSupplierEmail(requestDTO.getSupplierEmail());
        supplier.setCity(requestDTO.getCity());
        supplier.setCountry(requestDTO.getCountry());
        supplier.setState(requestDTO.getState());
        Supplier updated = supplierRepository.save(supplier);
        return modelMapper.map(updated, SupplierResponseDTO.class);
    }

    @Override
    public boolean deleteSupplier(Long supplierId) {
        if (supplierRepository.existsById(supplierId)) {
            supplierRepository.deleteById(supplierId);
            return true;
        }
        return false;
    }

    @Override
    public Page<SupplierResponseDTO> getAllSuppliers(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return supplierRepository.findAll(pageable).map(supplier -> modelMapper.map(supplier, SupplierResponseDTO.class));
    }

    @Override
    public Page<SupplierResponseDTO> searchSupplier(String search, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return supplierRepository.searchBySupplierName(search, pageable).map(supplier -> modelMapper.map(supplier, SupplierResponseDTO.class));
    }
}
