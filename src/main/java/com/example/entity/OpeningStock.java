package com.example.entity;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "opening_stocks")
public class OpeningStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long openingStockId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	private Integer quantity;

	private LocalDate openingDate;

	public Long getOpeningStockId() {
		return openingStockId;
	}
	public void setOpeningStockId(Long openingStockId) {
		this.openingStockId = openingStockId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public LocalDate getOpeningDate() {
		return openingDate;
	}
	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
	}
}
/*
 package com.example.enums;

public enum LeadSource {
	COLD_CALL,
	WEBSITE,
	REFERRAL,
	EMAIL_CAMPAIGN,
	SOCIAL_MEDIA,
	WALK_IN,
	OTHER
}
package com.example.enums;

public enum LeadStatus {
	NEW,
	CONTACTED,
	QUALIFIED,
	CONVERTED,
	LOST
}
package com.example.entity;

import com.example.enums.LeadSource;
import com.example.enums.LeadStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "leads")
public class Lead {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leadId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Column(unique = true)
	private String leadRef;

	@Enumerated(EnumType.STRING)
	private LeadStatus leadStatus;

	@Enumerated(EnumType.STRING)
	private LeadSource leadSource;

	public Long getLeadId() {
		return leadId;
	}
	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getLeadRef() {
		return leadRef;
	}
	public void setLeadRef(String leadRef) {
		this.leadRef = leadRef;
	}
	public LeadStatus getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(LeadStatus leadStatus) {
		this.leadStatus = leadStatus;
	}
	public LeadSource getLeadSource() {
		return leadSource;
	}
	public void setLeadSource(LeadSource leadSource) {
		this.leadSource = leadSource;
	}
}
package com.example.dto.request;

import com.example.enums.LeadSource;
import com.example.enums.LeadStatus;

public class LeadRequestDTO {

	private Long customerId;
	private String leadRef;
	private LeadStatus leadStatus;
	private LeadSource leadSource;

	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getLeadRef() {
		return leadRef;
	}
	public void setLeadRef(String leadRef) {
		this.leadRef = leadRef;
	}
	public LeadStatus getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(LeadStatus leadStatus) {
		this.leadStatus = leadStatus;
	}
	public LeadSource getLeadSource() {
		return leadSource;
	}
	public void setLeadSource(LeadSource leadSource) {
		this.leadSource = leadSource;
	}
}
package com.example.dto.response;

import com.example.enums.LeadSource;
import com.example.enums.LeadStatus;

public class LeadResponseDTO {

	private Long leadId;
	private String customerName;
	private String leadRef;
	private LeadStatus leadStatus;
	private LeadSource leadSource;

	public Long getLeadId() {
		return leadId;
	}
	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLeadRef() {
		return leadRef;
	}
	public void setLeadRef(String leadRef) {
		this.leadRef = leadRef;
	}
	public LeadStatus getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(LeadStatus leadStatus) {
		this.leadStatus = leadStatus;
	}
	public LeadSource getLeadSource() {
		return leadSource;
	}
	public void setLeadSource(LeadSource leadSource) {
		this.leadSource = leadSource;
	}
}
package com.example.repository;

import com.example.entity.Lead;
import com.example.enums.LeadStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {
	boolean existsByLeadRef(String leadRef);
	boolean existsByLeadRefAndLeadIdNot(String leadRef, Long leadId);
	List<Lead> findByCustomerCustomerId(Long customerId);
	List<Lead> findByLeadStatus(LeadStatus leadStatus);

	@Query("SELECT l FROM Lead l WHERE l.customer.customerName LIKE CONCAT('%', :search, '%') " +
	       "OR l.leadRef LIKE CONCAT('%', :search, '%')")
	Page<Lead> searchLeads(@Param("search") String search, Pageable pageable);
}
package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.LeadRequestDTO;
import com.example.dto.response.LeadResponseDTO;
import com.example.enums.LeadStatus;

public interface LeadService {
	LeadResponseDTO createLead(LeadRequestDTO requestDTO);
	Optional<LeadResponseDTO> getLeadById(Long leadId);
	LeadResponseDTO updateLead(Long leadId, LeadRequestDTO requestDTO);
	boolean deleteLead(Long leadId);
	Page<LeadResponseDTO> getAllLeads(int page, int size, String sortBy, String direction);
	Page<LeadResponseDTO> searchLeads(String search, int page, int size, String sortBy, String direction);
	List<LeadResponseDTO> getLeadsByCustomerId(Long customerId);
	List<LeadResponseDTO> getLeadsByStatus(LeadStatus status);
}
package com.example.serviceimple;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.request.LeadRequestDTO;
import com.example.dto.response.LeadResponseDTO;
import com.example.entity.Customer;
import com.example.entity.Lead;
import com.example.enums.LeadStatus;
import com.example.repository.CustomerRepository;
import com.example.repository.LeadRepository;
import com.example.service.LeadService;

@Service
public class LeadServiceImple implements LeadService {

	private final LeadRepository leadRepository;
	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper;

	public LeadServiceImple(LeadRepository leadRepository, CustomerRepository customerRepository, ModelMapper modelMapper) {
		this.leadRepository = leadRepository;
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public LeadResponseDTO createLead(LeadRequestDTO requestDTO) {
		Customer customer = customerRepository.findById(requestDTO.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));

		if (leadRepository.existsByLeadRef(requestDTO.getLeadRef())) {
			throw new RuntimeException("Lead Already exists with Ref:" + requestDTO.getLeadRef());
		}

		Lead lead = new Lead();
		lead.setCustomer(customer);
		lead.setLeadRef(requestDTO.getLeadRef());
		lead.setLeadStatus(requestDTO.getLeadStatus() == null ? LeadStatus.NEW : requestDTO.getLeadStatus());
		lead.setLeadSource(requestDTO.getLeadSource());

		Lead saved = leadRepository.save(lead);
		return toResponseDTO(saved);
	}

	@Override
	public Optional<LeadResponseDTO> getLeadById(Long leadId) {
		return leadRepository.findById(leadId).map(this::toResponseDTO);
	}

	@Override
	public LeadResponseDTO updateLead(Long leadId, LeadRequestDTO requestDTO) {
		Lead lead = leadRepository.findById(leadId)
				.orElseThrow(() -> new RuntimeException("Lead Not found with id:" + leadId));

		Customer customer = customerRepository.findById(requestDTO.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));

		if (leadRepository.existsByLeadRefAndLeadIdNot(requestDTO.getLeadRef(), leadId)) {
			throw new RuntimeException("Lead Already exists with Ref:" + requestDTO.getLeadRef());
		}

		lead.setCustomer(customer);
		lead.setLeadRef(requestDTO.getLeadRef());
		lead.setLeadStatus(requestDTO.getLeadStatus());
		lead.setLeadSource(requestDTO.getLeadSource());

		Lead updated = leadRepository.save(lead);
		return toResponseDTO(updated);
	}

	@Override
	public boolean deleteLead(Long leadId) {
		if (leadRepository.existsById(leadId)) {
			leadRepository.deleteById(leadId);
			return true;
		}
		return false;
	}

	@Override
	public Page<LeadResponseDTO> getAllLeads(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return leadRepository.findAll(pageable).map(this::toResponseDTO);
	}

	@Override
	public Page<LeadResponseDTO> searchLeads(String search, int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return leadRepository.searchLeads(search, pageable).map(this::toResponseDTO);
	}

	@Override
	public List<LeadResponseDTO> getLeadsByCustomerId(Long customerId) {
		return leadRepository.findByCustomerCustomerId(customerId).stream()
				.map(this::toResponseDTO).toList();
	}

	@Override
	public List<LeadResponseDTO> getLeadsByStatus(LeadStatus status) {
		return leadRepository.findByLeadStatus(status).stream()
				.map(this::toResponseDTO).toList();
	}

	private LeadResponseDTO toResponseDTO(Lead lead) {
		LeadResponseDTO dto = modelMapper.map(lead, LeadResponseDTO.class);
		dto.setCustomerName(lead.getCustomer().getCustomerName());
		return dto;
	}
}
package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.LeadRequestDTO;
import com.example.dto.response.LeadResponseDTO;
import com.example.enums.LeadStatus;
import com.example.service.LeadService;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

	private final LeadService leadService;

	public LeadController(LeadService leadService) {
		this.leadService = leadService;
	}

	@PostMapping("/save")
	public ResponseEntity<LeadResponseDTO> createLead(@RequestBody LeadRequestDTO requestDTO) {
		LeadResponseDTO saved = leadService.createLead(requestDTO);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{leadId}")
	public ResponseEntity<LeadResponseDTO> getLeadById(@PathVariable Long leadId) {
		LeadResponseDTO lead = leadService.getLeadById(leadId)
				.orElseThrow(() -> new RuntimeException("Lead Not found with id:" + leadId));
		return new ResponseEntity<>(lead, HttpStatus.OK);
	}

	@PutMapping("/update/{leadId}")
	public LeadResponseDTO updateLead(@PathVariable Long leadId, @RequestBody LeadRequestDTO requestDTO) {
		return leadService.updateLead(leadId, requestDTO);
	}

	@DeleteMapping("/{leadId}")
	public ResponseEntity<String> deleteLead(@PathVariable Long leadId) {
		boolean deleted = leadService.deleteLead(leadId);
		if (!deleted) {
			return new ResponseEntity<>("Lead not found with id:" + leadId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Lead deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public Page<LeadResponseDTO> getAllLeads(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "leadId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return leadService.getAllLeads(page, size, sortBy, direction);
	}

	@GetMapping("/searching")
	public Page<LeadResponseDTO> searchLeads(
			@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "leadId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return leadService.searchLeads(search, page, size, sortBy, direction);
	}

	@GetMapping("/customer/{customerId}")
	public List<LeadResponseDTO> getLeadsByCustomerId(@PathVariable Long customerId) {
		return leadService.getLeadsByCustomerId(customerId);
	}

	@GetMapping("/status/{status}")
	public List<LeadResponseDTO> getLeadsByStatus(@PathVariable LeadStatus status) {
		return leadService.getLeadsByStatus(status);
	}
}
package com.example.enums;

public enum EnquiryStatus {
	OPEN,
	CLOSED
}
package com.example.entity;

import com.example.enums.EnquiryStatus;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enquiries")
public class Enquiry {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long enquiryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product; // optional — nullable

	@Enumerated(EnumType.STRING)
	private EnquiryStatus status;

	public Long getEnquiryId() {
		return enquiryId;
	}
	public void setEnquiryId(Long enquiryId) {
		this.enquiryId = enquiryId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public EnquiryStatus getStatus() {
		return status;
	}
	public void setStatus(EnquiryStatus status) {
		this.status = status;
	}
}
package com.example.dto.request;

import com.example.enums.EnquiryStatus;

import java.time.LocalDate;

public class EnquiryRequestDTO {

	private Long customerId;
	private LocalDate date;
	private Long productId; // optional — can be null
	private EnquiryStatus status;

	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public EnquiryStatus getStatus() {
		return status;
	}
	public void setStatus(EnquiryStatus status) {
		this.status = status;
	}
}
package com.example.dto.response;

import com.example.enums.EnquiryStatus;

import java.time.LocalDate;

public class EnquiryResponseDTO {

	private Long enquiryId;
	private String customerName;
	private LocalDate date;
	private String productName; // will be null if no product was linked
	private EnquiryStatus status;

	public Long getEnquiryId() {
		return enquiryId;
	}
	public void setEnquiryId(Long enquiryId) {
		this.enquiryId = enquiryId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public EnquiryStatus getStatus() {
		return status;
	}
	public void setStatus(EnquiryStatus status) {
		this.status = status;
	}
}
package com.example.repository;

import com.example.entity.Enquiry;
import com.example.enums.EnquiryStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {
	List<Enquiry> findByCustomerCustomerId(Long customerId);
	List<Enquiry> findByStatus(EnquiryStatus status);

	@Query("SELECT e FROM Enquiry e WHERE e.customer.customerName LIKE CONCAT('%', :search, '%')")
	Page<Enquiry> searchEnquiries(@Param("search") String search, Pageable pageable);
}
package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.EnquiryRequestDTO;
import com.example.dto.response.EnquiryResponseDTO;
import com.example.enums.EnquiryStatus;

public interface EnquiryService {
	EnquiryResponseDTO createEnquiry(EnquiryRequestDTO requestDTO);
	Optional<EnquiryResponseDTO> getEnquiryById(Long enquiryId);
	EnquiryResponseDTO updateEnquiry(Long enquiryId, EnquiryRequestDTO requestDTO);
	boolean deleteEnquiry(Long enquiryId);
	Page<EnquiryResponseDTO> getAllEnquiries(int page, int size, String sortBy, String direction);
	Page<EnquiryResponseDTO> searchEnquiries(String search, int page, int size, String sortBy, String direction);
	List<EnquiryResponseDTO> getEnquiriesByCustomerId(Long customerId);
	List<EnquiryResponseDTO> getEnquiriesByStatus(EnquiryStatus status);
	EnquiryResponseDTO closeEnquiry(Long enquiryId);
}
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
package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.EnquiryRequestDTO;
import com.example.dto.response.EnquiryResponseDTO;
import com.example.enums.EnquiryStatus;
import com.example.service.EnquiryService;

import java.util.List;

@RestController
@RequestMapping("/api/enquiries")
public class EnquiryController {

	private final EnquiryService enquiryService;

	public EnquiryController(EnquiryService enquiryService) {
		this.enquiryService = enquiryService;
	}

	@PostMapping("/save")
	public ResponseEntity<EnquiryResponseDTO> createEnquiry(@RequestBody EnquiryRequestDTO requestDTO) {
		EnquiryResponseDTO saved = enquiryService.createEnquiry(requestDTO);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{enquiryId}")
	public ResponseEntity<EnquiryResponseDTO> getEnquiryById(@PathVariable Long enquiryId) {
		EnquiryResponseDTO enquiry = enquiryService.getEnquiryById(enquiryId)
				.orElseThrow(() -> new RuntimeException("Enquiry Not found with id:" + enquiryId));
		return new ResponseEntity<>(enquiry, HttpStatus.OK);
	}

	@PutMapping("/update/{enquiryId}")
	public EnquiryResponseDTO updateEnquiry(@PathVariable Long enquiryId, @RequestBody EnquiryRequestDTO requestDTO) {
		return enquiryService.updateEnquiry(enquiryId, requestDTO);
	}

	@DeleteMapping("/{enquiryId}")
	public ResponseEntity<String> deleteEnquiry(@PathVariable Long enquiryId) {
		boolean deleted = enquiryService.deleteEnquiry(enquiryId);
		if (!deleted) {
			return new ResponseEntity<>("Enquiry not found with id:" + enquiryId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Enquiry deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public Page<EnquiryResponseDTO> getAllEnquiries(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "enquiryId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return enquiryService.getAllEnquiries(page, size, sortBy, direction);
	}

	@GetMapping("/searching")
	public Page<EnquiryResponseDTO> searchEnquiries(
			@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "enquiryId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return enquiryService.searchEnquiries(search, page, size, sortBy, direction);
	}

	@GetMapping("/customer/{customerId}")
	public List<EnquiryResponseDTO> getEnquiriesByCustomerId(@PathVariable Long customerId) {
		return enquiryService.getEnquiriesByCustomerId(customerId);
	}

	@GetMapping("/status/{status}")
	public List<EnquiryResponseDTO> getEnquiriesByStatus(@PathVariable EnquiryStatus status) {
		return enquiryService.getEnquiriesByStatus(status);
	}

	@PutMapping("/close/{enquiryId}")
	public EnquiryResponseDTO closeEnquiry(@PathVariable Long enquiryId) {
		return enquiryService.closeEnquiry(enquiryId);
	}
}
 */
