package com.example.serviceimple;

import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.request.CustomerRequestDTO;
import com.example.dto.request.SubContactRequestDTO;
import com.example.dto.response.CustomerResponseDTO;
import com.example.entity.Customer;
import com.example.entity.SubContact;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;

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

		Customer customer = new Customer();
		customer.setCustomerName(requestDTO.getCustomerName());
		customer.setCustomerPhone(requestDTO.getCustomerPhone());
		customer.setCustomerEmail(requestDTO.getCustomerEmail());
		customer.setCity(requestDTO.getCity());
		customer.setCountry(requestDTO.getCountry());
		customer.setState(requestDTO.getState());

		if (requestDTO.getSubContacts() != null) {
			java.util.List<SubContact> subContacts = new ArrayList<>();
			for (SubContactRequestDTO contactDTO : requestDTO.getSubContacts()) {
				SubContact subContact = new SubContact();
				subContact.setContactPersonName(contactDTO.getContactPersonName());
				subContact.setContactPhone(contactDTO.getContactPhone());
				subContact.setContactEmail(contactDTO.getContactEmail());
				subContacts.add(subContact);
			}
			customer.setSubContacts(subContacts);
		}

		Customer saved = customerRepository.save(customer);
		return modelMapper.map(saved, CustomerResponseDTO.class);
	}

	@Override
	public Optional<CustomerResponseDTO> getCustomerById(Long customerId) {
		return customerRepository.findById(customerId).map(customer -> modelMapper.map(customer, CustomerResponseDTO.class));
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
		if (!customerRepository.existsById(customerId)) {
			return false;
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
	public CustomerResponseDTO addSubContact(Long customerId, SubContactRequestDTO requestDTO) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + customerId));

		SubContact subContact = new SubContact();
		subContact.setContactPersonName(requestDTO.getContactPersonName());
		subContact.setContactPhone(requestDTO.getContactPhone());
		subContact.setContactEmail(requestDTO.getContactEmail());

		if (customer.getSubContacts() == null) {
			customer.setSubContacts(new ArrayList<>());
		}
		customer.getSubContacts().add(subContact);

		Customer updated = customerRepository.save(customer);
		return modelMapper.map(updated, CustomerResponseDTO.class);
	}
}