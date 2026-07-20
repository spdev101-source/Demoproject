package com.example.serviceimple;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.dto.request.SupplierSubContactRequestDTO;
import com.example.dto.response.SupplierSubContactResponseDTO;
import com.example.entity.Supplier;
import com.example.entity.SupplierSubContact;
import com.example.repository.SupplierRepository;
import com.example.repository.SupplierSubContactRepository;
import com.example.service.SupplierSubContactService;

@Service
public class SupplierSubContactServiceImple implements SupplierSubContactService {

    private final SupplierSubContactRepository supplierSubContactRepository;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public SupplierSubContactServiceImple(SupplierSubContactRepository supplierSubContactRepository,
            SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierSubContactRepository = supplierSubContactRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SupplierSubContactResponseDTO createSubContact(SupplierSubContactRequestDTO requestDTO) {
        Supplier supplier = supplierRepository.findById(requestDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier Not found with id:" + requestDTO.getSupplierId()));
        if (supplierSubContactRepository.existsByContactEmailAndSupplierSupplierId(requestDTO.getContactEmail(), requestDTO.getSupplierId())) {
            throw new RuntimeException("Sub-contact Already exists with Email:" + requestDTO.getContactEmail());
        }
        SupplierSubContact subContact = new SupplierSubContact();
        subContact.setSupplier(supplier);
        subContact.setContactPersonName(requestDTO.getContactPersonName());
        subContact.setContactPhone(requestDTO.getContactPhone());
        subContact.setContactEmail(requestDTO.getContactEmail());
        SupplierSubContact saved = supplierSubContactRepository.save(subContact);
        SupplierSubContactResponseDTO dto = modelMapper.map(saved, SupplierSubContactResponseDTO.class);
        dto.setSupplierName(saved.getSupplier().getSupplierName());
        return dto;
    }

    @Override
    public Optional<SupplierSubContactResponseDTO> getSubContactById(Long subContactId) {
        return supplierSubContactRepository.findById(subContactId).map(subContact -> modelMapper.map(subContact, SupplierSubContactResponseDTO.class));
    }

    @Override
    public SupplierSubContactResponseDTO updateSubContact(Long subContactId, SupplierSubContactRequestDTO requestDTO) {
        SupplierSubContact subContact = supplierSubContactRepository.findById(subContactId)
                .orElseThrow(() -> new RuntimeException("Sub-contact Not found with id:" + subContactId));
        Supplier supplier = supplierRepository.findById(requestDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id:" + requestDTO.getSupplierId()));
        if (supplierSubContactRepository.existsByContactEmailAndSupplierSupplierIdAndSubContactIdNot(
                requestDTO.getContactEmail(), requestDTO.getSupplierId(), subContactId)) {
            throw new RuntimeException("Sub-contact Already exists with Email:" + requestDTO.getContactEmail());
        }
        subContact.setSupplier(supplier);
        subContact.setContactPersonName(requestDTO.getContactPersonName());
        subContact.setContactPhone(requestDTO.getContactPhone());
        subContact.setContactEmail(requestDTO.getContactEmail());
        SupplierSubContact updated = supplierSubContactRepository.save(subContact);
        SupplierSubContactResponseDTO dto = modelMapper.map(updated, SupplierSubContactResponseDTO.class);
        dto.setSupplierName(updated.getSupplier().getSupplierName());
        return dto;
    }

    @Override
    public boolean deleteSubContact(Long subContactId) {
        if (supplierSubContactRepository.existsById(subContactId)) {
            supplierSubContactRepository.deleteById(subContactId);
            return true;
        }
        return false;
    }

    @Override
    public List<SupplierSubContactResponseDTO> getSubContactsBySupplierId(Long supplierId) {
        return supplierSubContactRepository.findBySupplierSupplierId(supplierId).stream()
                .map(subContact -> modelMapper.map(subContact, SupplierSubContactResponseDTO.class)).toList();
    }
}
