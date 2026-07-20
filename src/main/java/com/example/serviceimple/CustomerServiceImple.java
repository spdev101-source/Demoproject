package com.example.serviceimple;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.request.CustomerRequestDTO;
import com.example.dto.response.CustomerResponseDTO;
import com.example.entity.Customer;
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

		if(customerRepository.existsByCustomerEmail(requestDTO.getCustomerEmail()))
		{
			throw new RuntimeException("Customer Already exists with email:"+requestDTO.getCustomerEmail());
		}
		if(customerRepository.existsByCustomerPhone(requestDTO.getCustomerPhone()))
		{
			throw new RuntimeException("Customer Already exists with phone:"+requestDTO.getCustomerPhone());

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
		if(customerRepository.existsByCustomerNameAndCustomerIdNot(requestDTO.getCustomerName(), customerId))
		{
			throw new RuntimeException("Customer Already exists with email:"+requestDTO.getCustomerEmail());

		}
		if(customerRepository.existsByCustomerPhoneAndCustomerIdNot(requestDTO.getCustomerPhone(), customerId))
		{
			throw new RuntimeException("Customer Already exists with phone:"+requestDTO.getCustomerPhone());

		}
		customer.setCustomerName(requestDTO.getCustomerName());
		customer.setCustomerEmail(requestDTO.getCustomerEmail());
		customer.setCustomerPhone(requestDTO.getCustomerPhone());
		customer.setCity(requestDTO.getCity());
		customer.setCountry(requestDTO.getCountry());
		customer.setState(requestDTO.getState());
		Customer updated=customerRepository.save(customer);
		return modelMapper.map(updated,CustomerResponseDTO.class);
	}

	@Override
	public boolean deleteCustomer(Long customerId) {
		if(customerRepository.existsById(customerId))
		{
			customerRepository.deleteById(customerId);
			return true;
		}
		return false;
	}

	@Override
	public Page<CustomerResponseDTO> getAllCustomers(int page, int size, String sortBy, String direction) {
		Sort sort=direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		Pageable pageable=PageRequest.of(page, size,sort);
		return customerRepository.findAll(pageable).map(customer->modelMapper.map(customer, CustomerResponseDTO.class));
	}

	@Override
    public Page<CustomerResponseDTO> searchCustomer(String search, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerRepository.searchByCustomerName(search, pageable).map(customer -> modelMapper.map(customer, CustomerResponseDTO.class));
    }

}
