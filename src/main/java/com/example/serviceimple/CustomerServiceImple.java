package com.example.serviceimple;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.dto.request.CustomerRequestDTO;
import com.example.dto.response.CustomerResponseDTO;
import com.example.dto.response.SubContactResponseDTO;
import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImple implements CustomerService {

	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper;

	public CustomerServiceImple(CustomerRepository customerRepository, ModelMapper modelMapper) {
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CustomerResponseDTO createCustomer(CustomerRequestDTO requestDTO) {
		if (customerRepository.existsByCustomerEmail(requestDTO.getCustomerEmail())) {
			throw new RuntimeException("Customer Already exists with Email:" + requestDTO.getCustomerEmail());
		}
		if (customerRepository.existsByCustomerPhone(requestDTO.getCustomerPhone())) {
			throw new RuntimeException("Customer Already exists with Phone:" + requestDTO.getCustomerPhone());
		}
		Customer customer = modelMapper.map(requestDTO, Customer.class);
		Customer saved = customerRepository.save(customer);
		return modelMapper.map(saved, CustomerResponseDTO.class);
	}

	@Override
	public Optional<CustomerResponseDTO> getCustomerById(Long customerId) {
		return customerRepository.findById(customerId)
				.map(customer -> modelMapper.map(customer, CustomerResponseDTO.class));
	}

	@Override
	public CustomerResponseDTO updateCustomer(Long customerId, CustomerRequestDTO requestDTO) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found with id:" + customerId));
		if (customerRepository.existsByCustomerEmailAndCustomerIdNot(requestDTO.getCustomerEmail(), customerId)) {
			throw new RuntimeException("Customer Already exists with Email:" + requestDTO.getCustomerEmail());
		}
		if (customerRepository.existsByCustomerPhoneAndCustomerIdNot(requestDTO.getCustomerPhone(), customerId)) {
			throw new RuntimeException("Customer Already exists with Phone:" + requestDTO.getCustomerPhone());
		}
		customer.setCustomerName(requestDTO.getCustomerName());
		customer.setCustomerPhone(requestDTO.getCustomerPhone());
		customer.setCustomerEmail(requestDTO.getCustomerEmail());
		customer.setCity(requestDTO.getCity());
		customer.setCountry(requestDTO.getCountry());
		customer.setState(requestDTO.getState());
		Customer updated = customerRepository.save(customer);
		return modelMapper.map(updated, CustomerResponseDTO.class);
	}

	@Override
	public boolean deleteCustomer(Long customerId) {
		Optional<Customer> customerOpt = customerRepository.findById(customerId);
		if (customerOpt.isEmpty()) {
			return false;
		}
		Customer customer = customerOpt.get();
		if (!customer.getSubContacts().isEmpty()) {
			throw new RuntimeException("Cannot delete customer, sub-contacts exist");
		}
		customerRepository.deleteById(customerId);
		return true;
	}

	@Override
	public Page<CustomerResponseDTO> getAllCustomers(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return customerRepository.findAll(pageable).map(customer -> modelMapper.map(customer, CustomerResponseDTO.class));
	}

	@Override
	public Page<CustomerResponseDTO> searchCustomer(String search, int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return customerRepository.searchByCustomerName(search, pageable).map(customer -> modelMapper.map(customer, CustomerResponseDTO.class));
	}
	@Override
	public Optional<CustomerResponseDTO> getCustomerWithContacts(Long customerId) {
		return customerRepository.findById(customerId).map(customer -> {
			CustomerResponseDTO dto = modelMapper.map(customer, CustomerResponseDTO.class);
			List<SubContactResponseDTO> contacts = customer.getSubContacts().stream()
					.map(sc -> {
						SubContactResponseDTO scDto = modelMapper.map(sc, SubContactResponseDTO.class);
						scDto.setCustomerName(customer.getCustomerName());
						return scDto;
					})
					.toList();
			dto.setSubContacts(contacts);
			return dto;
		});
	}
}