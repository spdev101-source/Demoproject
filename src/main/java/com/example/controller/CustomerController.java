package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.CustomerRequestDTO;
import com.example.dto.request.SubContactRequestDTO;
import com.example.dto.response.CustomerResponseDTO;
import com.example.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping("/save")
	public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO requestDTO) {
		CustomerResponseDTO saved = customerService.createCustomer(requestDTO);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{customerId}")
	public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long customerId) {
		CustomerResponseDTO customer = customerService.getCustomerById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + customerId));
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@PutMapping("/update/{customerId}")
	public CustomerResponseDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerRequestDTO requestDTO) {
		return customerService.updateCustomer(customerId, requestDTO);
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
		boolean deleted = customerService.deleteCustomer(customerId);
		if (!deleted) {
			return new ResponseEntity<>("Customer not found with id:" + customerId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Customer deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public Page<CustomerResponseDTO> getAllCustomers(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "customerId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return customerService.getAllCustomers(page, size, sortBy, direction);
	}

	@GetMapping("/searching")
	public Page<CustomerResponseDTO> searchCustomer(
			@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "customerId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return customerService.searchCustomer(search, page, size, sortBy, direction);
	}

	@PostMapping("/{customerId}/subcontacts")
	public ResponseEntity<CustomerResponseDTO> addSubContact(@PathVariable Long customerId, @RequestBody SubContactRequestDTO requestDTO) {
		CustomerResponseDTO updated = customerService.addSubContact(customerId, requestDTO);
		return new ResponseEntity<>(updated, HttpStatus.CREATED);
	}
	/*
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

	if (requestDTO.getSubContacts() != null) {
		if (customer.getSubContacts() == null) {
			customer.setSubContacts(new ArrayList<>());
		} else {
			customer.getSubContacts().clear();
		}
		for (SubContactRequestDTO contactDTO : requestDTO.getSubContacts()) {
			SubContact subContact = new SubContact();
			subContact.setContactPersonName(contactDTO.getContactPersonName());
			subContact.setContactPhone(contactDTO.getContactPhone());
			subContact.setContactEmail(contactDTO.getContactEmail());
			customer.getSubContacts().add(subContact);
		}
	}

	Customer updated = customerRepository.save(customer);
	return modelMapper.map(updated, CustomerResponseDTO.class);
}
	 */
}