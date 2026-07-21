package com.example.serviceimple;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.dto.request.SubContactRequestDTO;
import com.example.dto.response.CustomerResponseDTO;
import com.example.dto.response.SubContactResponseDTO;
import com.example.entity.Customer;
import com.example.entity.SubContact;
import com.example.repository.CustomerRepository;
import com.example.repository.SubContactRepository;
import com.example.service.SubContactService;

@Service
public class SubContactServiceImple implements SubContactService {

    private final SubContactRepository subContactRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public SubContactServiceImple(SubContactRepository subContactRepository, CustomerRepository customerRepository,
            ModelMapper modelMapper) {
        this.subContactRepository = subContactRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SubContactResponseDTO createSubContact(SubContactRequestDTO requestDTO) {
        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));
        if (subContactRepository.existsByContactEmailAndCustomerCustomerId(requestDTO.getContactEmail(), requestDTO.getCustomerId())) {
            throw new RuntimeException("Sub-contact Already exists with Email:" + requestDTO.getContactEmail());
        }
        SubContact subContact = new SubContact();
        subContact.setCustomer(customer);
        subContact.setContactPersonName(requestDTO.getContactPersonName());
        subContact.setContactPhone(requestDTO.getContactPhone());
        subContact.setContactEmail(requestDTO.getContactEmail());
        SubContact saved = subContactRepository.save(subContact);
        SubContactResponseDTO dto = modelMapper.map(saved, SubContactResponseDTO.class);
        //dto.setCustomer(modelMapper.map(saved.getCustomer(), CustomerResponseDTO.class));
        return dto;
    }

    @Override
    public Optional<SubContactResponseDTO> getSubContactById(Long subContactId) {
        return subContactRepository.findById(subContactId).map(subContact -> modelMapper.map(subContact, SubContactResponseDTO.class));
    }

    @Override
    public SubContactResponseDTO updateSubContact(Long subContactId, SubContactRequestDTO requestDTO) {
        SubContact subContact = subContactRepository.findById(subContactId)
                .orElseThrow(() -> new RuntimeException("Sub-contact Not found with id:" + subContactId));
        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id:" + requestDTO.getCustomerId()));
        if (subContactRepository.existsByContactEmailAndCustomerCustomerIdAndSubContactIdNot(
                requestDTO.getContactEmail(), requestDTO.getCustomerId(), subContactId)) {
            throw new RuntimeException("Sub-contact Already exists with Email:" + requestDTO.getContactEmail());
        }
        subContact.setCustomer(customer);
        subContact.setContactPersonName(requestDTO.getContactPersonName());
        subContact.setContactPhone(requestDTO.getContactPhone());
        subContact.setContactEmail(requestDTO.getContactEmail());
        SubContact updated = subContactRepository.save(subContact);
        SubContactResponseDTO dto = modelMapper.map(updated, SubContactResponseDTO.class);
       // dto.setCustomer(modelMapper.map(updated.getCustomer(), CustomerResponseDTO.class));
        return dto;
    }

    @Override
    public boolean deleteSubContact(Long subContactId) {
        if (subContactRepository.existsById(subContactId)) {
            subContactRepository.deleteById(subContactId);
            return true;
        }
        return false;
    }

    @Override
    public List<SubContactResponseDTO> getSubContactsByCustomerId(Long customerId) {
        return subContactRepository.findByCustomerCustomerId(customerId).stream()
                .map(subContact -> modelMapper.map(subContact, SubContactResponseDTO.class)).toList();
    }
}
