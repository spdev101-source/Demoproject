package com.example.serviceimple;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.request.EnquiryRequestDTO;
import com.example.dto.response.EnquiryResponseDTO;
import com.example.entity.Customer;
import com.example.entity.Enquiry;
import com.example.entity.Product;
import com.example.enums.EnquiryStatus;
import com.example.repository.CustomerRepository;
import com.example.repository.EnquiryRepository;
import com.example.repository.ProductRepository;
import com.example.service.EnquiryService;

@Service
public class EnquiryServiceImple implements EnquiryService {

	private final EnquiryRepository enquiryRepository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;

	public EnquiryServiceImple(EnquiryRepository enquiryRepository, CustomerRepository customerRepository,
			ProductRepository productRepository, ModelMapper modelMapper) {
		this.enquiryRepository = enquiryRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public EnquiryResponseDTO createEnquiry(EnquiryRequestDTO requestDTO) {
		Customer customer = customerRepository.findById(requestDTO.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));

		Product product = null;
		if (requestDTO.getProductId() != null) {
			product = productRepository.findById(requestDTO.getProductId())
					.orElseThrow(() -> new RuntimeException("Product Not found with id:" + requestDTO.getProductId()));
		}

		Enquiry enquiry = new Enquiry();
		enquiry.setCustomer(customer);
		enquiry.setDate(requestDTO.getDate());
		enquiry.setProduct(product);
		enquiry.setStatus(requestDTO.getStatus() == null ? EnquiryStatus.OPEN : requestDTO.getStatus());

		Enquiry saved = enquiryRepository.save(enquiry);
		return toResponseDTO(saved);
	}

	@Override
	public Optional<EnquiryResponseDTO> getEnquiryById(Long enquiryId) {
		return enquiryRepository.findById(enquiryId).map(this::toResponseDTO);
	}

	@Override
	public EnquiryResponseDTO updateEnquiry(Long enquiryId, EnquiryRequestDTO requestDTO) {
		Enquiry enquiry = enquiryRepository.findById(enquiryId)
				.orElseThrow(() -> new RuntimeException("Enquiry Not found with id:" + enquiryId));

		Customer customer = customerRepository.findById(requestDTO.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));

		Product product = null;
		if (requestDTO.getProductId() != null) {
			product = productRepository.findById(requestDTO.getProductId())
					.orElseThrow(() -> new RuntimeException("Product Not found with id:" + requestDTO.getProductId()));
		}

		enquiry.setCustomer(customer);
		enquiry.setDate(requestDTO.getDate());
		enquiry.setProduct(product);
		enquiry.setStatus(requestDTO.getStatus());

		Enquiry updated = enquiryRepository.save(enquiry);
		return toResponseDTO(updated);
	}

	@Override
	public boolean deleteEnquiry(Long enquiryId) {
		if (enquiryRepository.existsById(enquiryId)) {
			enquiryRepository.deleteById(enquiryId);
			return true;
		}
		return false;
	}

	@Override
	public Page<EnquiryResponseDTO> getAllEnquiries(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return enquiryRepository.findAll(pageable).map(this::toResponseDTO);
	}

	@Override
	public Page<EnquiryResponseDTO> searchEnquiries(String search, int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return enquiryRepository.searchEnquiries(search, pageable).map(this::toResponseDTO);
	}

	@Override
	public List<EnquiryResponseDTO> getEnquiriesByCustomerId(Long customerId) {
		return enquiryRepository.findByCustomerCustomerId(customerId).stream()
				.map(this::toResponseDTO).toList();
	}

	@Override
	public List<EnquiryResponseDTO> getEnquiriesByStatus(EnquiryStatus status) {
		return enquiryRepository.findByStatus(status).stream()
				.map(this::toResponseDTO).toList();
	}

	@Override
	public EnquiryResponseDTO closeEnquiry(Long enquiryId) {
		Enquiry enquiry = enquiryRepository.findById(enquiryId)
				.orElseThrow(() -> new RuntimeException("Enquiry Not found with id:" + enquiryId));
		enquiry.setStatus(EnquiryStatus.CLOSED);
		Enquiry updated = enquiryRepository.save(enquiry);
		return toResponseDTO(updated);
	}

	private EnquiryResponseDTO toResponseDTO(Enquiry enquiry) {
		EnquiryResponseDTO dto = modelMapper.map(enquiry, EnquiryResponseDTO.class);
		dto.setCustomerName(enquiry.getCustomer().getCustomerName());
		dto.setProductName(enquiry.getProduct() != null ? enquiry.getProduct().getProductName() : null);
		return dto;
	}
}