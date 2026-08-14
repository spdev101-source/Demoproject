package com.example.entity;

import com.example.enums.WarehouseType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="warehouses")
public class Warehouse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long warehouseId;
	private String warehouseName;
	private String location;
	@Enumerated(EnumType.STRING)
	private WarehouseType type;
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public WarehouseType getType() {
		return type;
	}
	public void setType(WarehouseType type) {
		this.type = type;
	}
	/*
	 package com.example.enums;

public enum QuotationStatus {
	DRAFT,
	SENT,
	ACCEPTED,
	REJECTED
}
package com.example.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.enums.QuotationStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "quotations")
public class Quotation extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long quotationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lead_id")
	private Lead lead;

	private LocalDate quotationDate;
	private LocalDate validTillDate;

	@Enumerated(EnumType.STRING)
	private QuotationStatus status;

	private BigDecimal totalAmount;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "quotation_id")
	private List<QuotationItem> quotationItems;

	public Long getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(Long quotationId) {
		this.quotationId = quotationId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Lead getLead() {
		return lead;
	}
	public void setLead(Lead lead) {
		this.lead = lead;
	}
	public LocalDate getQuotationDate() {
		return quotationDate;
	}
	public void setQuotationDate(LocalDate quotationDate) {
		this.quotationDate = quotationDate;
	}
	public LocalDate getValidTillDate() {
		return validTillDate;
	}
	public void setValidTillDate(LocalDate validTillDate) {
		this.validTillDate = validTillDate;
	}
	public QuotationStatus getStatus() {
		return status;
	}
	public void setStatus(QuotationStatus status) {
		this.status = status;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<QuotationItem> getQuotationItems() {
		return quotationItems;
	}
	public void setQuotationItems(List<QuotationItem> quotationItems) {
		this.quotationItems = quotationItems;
	}
}
package com.example.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "quotation_items")
public class QuotationItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long quotationItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	private Integer quantity;
	private BigDecimal unitPrice;
	private BigDecimal taxPercentage;
	private BigDecimal lineTotal;

	public Long getQuotationItemId() {
		return quotationItemId;
	}
	public void setQuotationItemId(Long quotationItemId) {
		this.quotationItemId = quotationItemId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getTaxPercentage() {
		return taxPercentage;
	}
	public void setTaxPercentage(BigDecimal taxPercentage) {
		this.taxPercentage = taxPercentage;
	}
	public BigDecimal getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}
}
package com.example.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.example.enums.QuotationStatus;

public class QuotationRequestDTO {

	private Long customerId;
	private Long leadId; // optional
	private LocalDate quotationDate;
	private LocalDate validTillDate;
	private QuotationStatus status;
	private List<QuotationItemRequestDTO> quotationItems;

	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getLeadId() {
		return leadId;
	}
	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}
	public LocalDate getQuotationDate() {
		return quotationDate;
	}
	public void setQuotationDate(LocalDate quotationDate) {
		this.quotationDate = quotationDate;
	}
	public LocalDate getValidTillDate() {
		return validTillDate;
	}
	public void setValidTillDate(LocalDate validTillDate) {
		this.validTillDate = validTillDate;
	}
	public QuotationStatus getStatus() {
		return status;
	}
	public void setStatus(QuotationStatus status) {
		this.status = status;
	}
	public List<QuotationItemRequestDTO> getQuotationItems() {
		return quotationItems;
	}
	public void setQuotationItems(List<QuotationItemRequestDTO> quotationItems) {
		this.quotationItems = quotationItems;
	}
}
package com.example.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.example.enums.QuotationStatus;
import java.math.BigDecimal;

public class QuotationResponseDTO {

	private Long quotationId;
	private String customerName;
	private String leadRef;
	private LocalDate quotationDate;
	private LocalDate validTillDate;
	private QuotationStatus status;
	private BigDecimal totalAmount;
	private List<QuotationItemResponseDTO> quotationItems;

	public Long getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(Long quotationId) {
		this.quotationId = quotationId;
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
	public LocalDate getQuotationDate() {
		return quotationDate;
	}
	public void setQuotationDate(LocalDate quotationDate) {
		this.quotationDate = quotationDate;
	}
	public LocalDate getValidTillDate() {
		return validTillDate;
	}
	public void setValidTillDate(LocalDate validTillDate) {
		this.validTillDate = validTillDate;
	}
	public QuotationStatus getStatus() {
		return status;
	}
	public void setStatus(QuotationStatus status) {
		this.status = status;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<QuotationItemResponseDTO> getQuotationItems() {
		return quotationItems;
	}
	public void setQuotationItems(List<QuotationItemResponseDTO> quotationItems) {
		this.quotationItems = quotationItems;
	}
}
package com.example.dto.request;

import java.math.BigDecimal;

public class QuotationItemRequestDTO {

	private Long productId;
	private Integer quantity;
	private BigDecimal unitPrice;
	private BigDecimal taxPercentage;

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getTaxPercentage() {
		return taxPercentage;
	}
	public void setTaxPercentage(BigDecimal taxPercentage) {
		this.taxPercentage = taxPercentage;
	}
}
package com.example.dto.response;

import java.math.BigDecimal;

public class QuotationItemResponseDTO {

	private Long quotationItemId;
	private String productName;
	private Integer quantity;
	private BigDecimal unitPrice;
	private BigDecimal taxPercentage;
	private BigDecimal lineTotal;

	public Long getQuotationItemId() {
		return quotationItemId;
	}
	public void setQuotationItemId(Long quotationItemId) {
		this.quotationItemId = quotationItemId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getTaxPercentage() {
		return taxPercentage;
	}
	public void setTaxPercentage(BigDecimal taxPercentage) {
		this.taxPercentage = taxPercentage;
	}
	public BigDecimal getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}
}
package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Quotation;

public interface QuotationRepository extends JpaRepository<Quotation, Long> {

	@Query("SELECT q FROM Quotation q WHERE q.customer.customerName LIKE CONCAT('%', :search, '%')")
	Page<Quotation> searchQuotations(@Param("search") String search, Pageable pageable);
}
package com.example.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.QuotationRequestDTO;
import com.example.dto.response.QuotationResponseDTO;
import com.example.enums.QuotationStatus;

public interface QuotationService {
	QuotationResponseDTO createQuotation(QuotationRequestDTO requestDTO);
	Optional<QuotationResponseDTO> getQuotationById(Long quotationId);
	QuotationResponseDTO updateQuotation(Long quotationId, QuotationRequestDTO requestDTO);
	boolean deleteQuotation(Long quotationId);
	Page<QuotationResponseDTO> getAllQuotations(int page, int size, String sortBy, String direction);
	Page<QuotationResponseDTO> searchQuotations(String search, int page, int size, String sortBy, String direction);
	QuotationResponseDTO changeStatus(Long quotationId, QuotationStatus newStatus);
}
package com.example.serviceimple;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.request.QuotationItemRequestDTO;
import com.example.dto.request.QuotationRequestDTO;
import com.example.dto.response.QuotationItemResponseDTO;
import com.example.dto.response.QuotationResponseDTO;
import com.example.entity.Customer;
import com.example.entity.Lead;
import com.example.entity.Product;
import com.example.entity.Quotation;
import com.example.entity.QuotationItem;
import com.example.enums.QuotationStatus;
import com.example.repository.CustomerRepository;
import com.example.repository.LeadRepository;
import com.example.repository.ProductRepository;
import com.example.repository.QuotationRepository;
import com.example.service.QuotationService;

@Service
public class QuotationServiceImple implements QuotationService {

	private final QuotationRepository quotationRepository;
	private final CustomerRepository customerRepository;
	private final LeadRepository leadRepository;
	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;

	public QuotationServiceImple(QuotationRepository quotationRepository, CustomerRepository customerRepository,
			LeadRepository leadRepository, ProductRepository productRepository, ModelMapper modelMapper) {
		this.quotationRepository = quotationRepository;
		this.customerRepository = customerRepository;
		this.leadRepository = leadRepository;
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public QuotationResponseDTO createQuotation(QuotationRequestDTO requestDTO) {
		Customer customer = customerRepository.findById(requestDTO.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));

		Lead lead = null;
		if (requestDTO.getLeadId() != null) {
			lead = leadRepository.findById(requestDTO.getLeadId())
					.orElseThrow(() -> new RuntimeException("Lead Not found with id:" + requestDTO.getLeadId()));
		}

		if (requestDTO.getQuotationDate() == null || requestDTO.getQuotationDate().isAfter(LocalDate.now())) {
			throw new RuntimeException("Quotation date cannot be in the future");
		}
		if (requestDTO.getValidTillDate() != null && requestDTO.getValidTillDate().isBefore(requestDTO.getQuotationDate())) {
			throw new RuntimeException("Valid till date cannot be before quotation date");
		}

		Quotation quotation = new Quotation();
		quotation.setCustomer(customer);
		quotation.setLead(lead);
		quotation.setQuotationDate(requestDTO.getQuotationDate());
		quotation.setValidTillDate(requestDTO.getValidTillDate());
		quotation.setStatus(requestDTO.getStatus() == null ? QuotationStatus.DRAFT : requestDTO.getStatus());

		List<QuotationItem> items = new ArrayList<>();
		BigDecimal totalAmount = BigDecimal.ZERO;

		if (requestDTO.getQuotationItems() != null) {
			for (QuotationItemRequestDTO itemDTO : requestDTO.getQuotationItems()) {
				Product product = productRepository.findById(itemDTO.getProductId())
						.orElseThrow(() -> new RuntimeException("Product Not found with id:" + itemDTO.getProductId()));

				if (itemDTO.getQuantity() == null || itemDTO.getQuantity() <= 0) {
					throw new RuntimeException("Quantity must be greater than 0 for product:" + product.getProductName());
				}
				if (itemDTO.getUnitPrice() == null || itemDTO.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
					throw new RuntimeException("Unit price cannot be negative for product:" + product.getProductName());
				}

				BigDecimal tax = itemDTO.getTaxPercentage() == null ? BigDecimal.ZERO : itemDTO.getTaxPercentage();
				BigDecimal baseAmount = itemDTO.getUnitPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
				BigDecimal taxAmount = baseAmount.multiply(tax).divide(BigDecimal.valueOf(100));
				BigDecimal lineTotal = baseAmount.add(taxAmount);

				QuotationItem item = new QuotationItem();
				item.setProduct(product);
				item.setQuantity(itemDTO.getQuantity());
				item.setUnitPrice(itemDTO.getUnitPrice());
				item.setTaxPercentage(tax);
				item.setLineTotal(lineTotal);

				items.add(item);
				totalAmount = totalAmount.add(lineTotal);
			}
		}

		quotation.setQuotationItems(items);
		quotation.setTotalAmount(totalAmount);

		Quotation saved = quotationRepository.save(quotation);
		return toResponseDTO(saved);
	}

	@Override
	public Optional<QuotationResponseDTO> getQuotationById(Long quotationId) {
		return quotationRepository.findById(quotationId).map(this::toResponseDTO);
	}

	@Override
	public QuotationResponseDTO updateQuotation(Long quotationId, QuotationRequestDTO requestDTO) {
		Quotation quotation = quotationRepository.findById(quotationId)
				.orElseThrow(() -> new RuntimeException("Quotation Not found with id:" + quotationId));

		if (quotation.getStatus() == QuotationStatus.ACCEPTED || quotation.getStatus() == QuotationStatus.REJECTED) {
			throw new RuntimeException("Cannot edit a quotation that is already Accepted or Rejected");
		}

		Customer customer = customerRepository.findById(requestDTO.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));

		Lead lead = null;
		if (requestDTO.getLeadId() != null) {
			lead = leadRepository.findById(requestDTO.getLeadId())
					.orElseThrow(() -> new RuntimeException("Lead Not found with id:" + requestDTO.getLeadId()));
		}

		if (requestDTO.getQuotationDate() == null || requestDTO.getQuotationDate().isAfter(LocalDate.now())) {
			throw new RuntimeException("Quotation date cannot be in the future");
		}
		if (requestDTO.getValidTillDate() != null && requestDTO.getValidTillDate().isBefore(requestDTO.getQuotationDate())) {
			throw new RuntimeException("Valid till date cannot be before quotation date");
		}

		quotation.setCustomer(customer);
		quotation.setLead(lead);
		quotation.setQuotationDate(requestDTO.getQuotationDate());
		quotation.setValidTillDate(requestDTO.getValidTillDate());

		if (quotation.getQuotationItems() != null) {
			quotation.getQuotationItems().clear();
		} else {
			quotation.setQuotationItems(new ArrayList<>());
		}

		BigDecimal totalAmount = BigDecimal.ZERO;
		if (requestDTO.getQuotationItems() != null) {
			for (QuotationItemRequestDTO itemDTO : requestDTO.getQuotationItems()) {
				Product product = productRepository.findById(itemDTO.getProductId())
						.orElseThrow(() -> new RuntimeException("Product Not found with id:" + itemDTO.getProductId()));

				if (itemDTO.getQuantity() == null || itemDTO.getQuantity() <= 0) {
					throw new RuntimeException("Quantity must be greater than 0 for product:" + product.getProductName());
				}
				if (itemDTO.getUnitPrice() == null || itemDTO.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
					throw new RuntimeException("Unit price cannot be negative for product:" + product.getProductName());
				}

				BigDecimal tax = itemDTO.getTaxPercentage() == null ? BigDecimal.ZERO : itemDTO.getTaxPercentage();
				BigDecimal baseAmount = itemDTO.getUnitPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
				BigDecimal taxAmount = baseAmount.multiply(tax).divide(BigDecimal.valueOf(100));
				BigDecimal lineTotal = baseAmount.add(taxAmount);

				QuotationItem item = new QuotationItem();
				item.setQuotation(quotation);
				item.setProduct(product);
				item.setQuantity(itemDTO.getQuantity());
				item.setUnitPrice(itemDTO.getUnitPrice());
				item.setTaxPercentage(tax);
				item.setLineTotal(lineTotal);

				quotation.getQuotationItems().add(item);
				totalAmount = totalAmount.add(lineTotal);
			}
		}

		quotation.setTotalAmount(totalAmount);

		Quotation updated = quotationRepository.save(quotation);
		return toResponseDTO(updated);
	}

	@Override
	public boolean deleteQuotation(Long quotationId) {
		if (quotationRepository.existsById(quotationId)) {
			quotationRepository.deleteById(quotationId);
			return true;
		}
		return false;
	}

	@Override
	public Page<QuotationResponseDTO> getAllQuotations(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return quotationRepository.findAll(pageable).map(this::toResponseDTO);
	}

	@Override
	public Page<QuotationResponseDTO> searchQuotations(String search, int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return quotationRepository.searchQuotations(search, pageable).map(this::toResponseDTO);
	}

	@Override
	public QuotationResponseDTO changeStatus(Long quotationId, QuotationStatus newStatus) {
		Quotation quotation = quotationRepository.findById(quotationId)
				.orElseThrow(() -> new RuntimeException("Quotation Not found with id:" + quotationId));

		if (quotation.getStatus() == QuotationStatus.ACCEPTED || quotation.getStatus() == QuotationStatus.REJECTED) {
			throw new RuntimeException("Cannot change status of a quotation that is already Accepted or Rejected");
		}

		quotation.setStatus(newStatus);
		Quotation updated = quotationRepository.save(quotation);
		return toResponseDTO(updated);
	}

	private QuotationResponseDTO toResponseDTO(Quotation quotation) {
		QuotationResponseDTO dto = modelMapper.map(quotation, QuotationResponseDTO.class);
		dto.setCustomerName(quotation.getCustomer().getCustomerName());
		dto.setLeadRef(quotation.getLead() != null ? quotation.getLead().getLeadRef() : null);

		List<QuotationItemResponseDTO> itemDTOs = new ArrayList<>();
		if (quotation.getQuotationItems() != null) {
			for (QuotationItem item : quotation.getQuotationItems()) {
				QuotationItemResponseDTO itemDTO = modelMapper.map(item, QuotationItemResponseDTO.class);
				itemDTO.setProductName(item.getProduct().getProductName());
				itemDTOs.add(itemDTO);
			}
		}
		dto.setQuotationItems(itemDTOs);
		return dto;
	}
}
package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.QuotationRequestDTO;
import com.example.dto.response.QuotationResponseDTO;
import com.example.enums.QuotationStatus;
import com.example.service.QuotationService;

@RestController
@RequestMapping("/api/quotations")
public class QuotationController {

	private final QuotationService quotationService;

	public QuotationController(QuotationService quotationService) {
		this.quotationService = quotationService;
	}

	@PostMapping("/save")
	public ResponseEntity<QuotationResponseDTO> createQuotation(@RequestBody QuotationRequestDTO requestDTO) {
		QuotationResponseDTO saved = quotationService.createQuotation(requestDTO);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{quotationId}")
	public ResponseEntity<QuotationResponseDTO> getQuotationById(@PathVariable Long quotationId) {
		QuotationResponseDTO quotation = quotationService.getQuotationById(quotationId)
				.orElseThrow(() -> new RuntimeException("Quotation Not found with id:" + quotationId));
		return new ResponseEntity<>(quotation, HttpStatus.OK);
	}

	@PutMapping("/update/{quotationId}")
	public QuotationResponseDTO updateQuotation(@PathVariable Long quotationId, @RequestBody QuotationRequestDTO requestDTO) {
		return quotationService.updateQuotation(quotationId, requestDTO);
	}

	@DeleteMapping("/{quotationId}")
	public ResponseEntity<String> deleteQuotation(@PathVariable Long quotationId) {
		boolean deleted = quotationService.deleteQuotation(quotationId);
		if (!deleted) {
			return new ResponseEntity<>("Quotation not found with id:" + quotationId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Quotation deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public Page<QuotationResponseDTO> getAllQuotations(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "quotationId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return quotationService.getAllQuotations(page, size, sortBy, direction);
	}

	@GetMapping("/searching")
	public Page<QuotationResponseDTO> searchQuotations(
			@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "quotationId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return quotationService.searchQuotations(search, page, size, sortBy, direction);
	}

	@PutMapping("/status/{quotationId}")
	public QuotationResponseDTO changeStatus(@PathVariable Long quotationId, @RequestParam QuotationStatus status) {
		return quotationService.changeStatus(quotationId, status);
	}
}
POST /api/quotations/save
{
  "customerId": 1,
  "quotationDate": "2026-08-01",
  "validTillDate": "2026-09-01",
  "quotationItems": [
    { "productId": 1, "quantity": 2, "unitPrice": 500.00, "taxPercentage": 18 },
    { "productId": 2, "quantity": 1, "unitPrice": 1000.00, "taxPercentage": 18 }
  ]
}
@Override
public QuotationResponseDTO createQuotation(QuotationRequestDTO requestDTO) {
	Customer customer = customerRepository.findById(requestDTO.getCustomerId())
			.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));

	Lead lead = null;
	if (requestDTO.getLeadId() != null) {
		lead = leadRepository.findById(requestDTO.getLeadId())
				.orElseThrow(() -> new RuntimeException("Lead Not found with id:" + requestDTO.getLeadId()));
	}

	if (requestDTO.getQuotationDate() == null || requestDTO.getQuotationDate().isAfter(LocalDate.now())) {
		throw new RuntimeException("Quotation date cannot be in the future");
	}
	if (requestDTO.getValidTillDate() != null && requestDTO.getValidTillDate().isBefore(requestDTO.getQuotationDate())) {
		throw new RuntimeException("Valid till date cannot be before quotation date");
	}

	Quotation quotation = new Quotation();
	quotation.setCustomer(customer);
	quotation.setLead(lead);
	quotation.setQuotationDate(requestDTO.getQuotationDate());
	quotation.setValidTillDate(requestDTO.getValidTillDate());
	quotation.setStatus(requestDTO.getStatus() == null ? QuotationStatus.DRAFT : requestDTO.getStatus());

	List<QuotationItem> items = new ArrayList<>();
	BigDecimal totalAmount = BigDecimal.ZERO;

	if (requestDTO.getQuotationItems() != null) {
		for (QuotationItemRequestDTO itemDTO : requestDTO.getQuotationItems()) {
			Product product = productRepository.findById(itemDTO.getProductId())
					.orElseThrow(() -> new RuntimeException("Product Not found with id:" + itemDTO.getProductId()));

			if (itemDTO.getQuantity() == null || itemDTO.getQuantity() <= 0) {
				throw new RuntimeException("Quantity must be greater than 0 for product:" + product.getProductName());
			}
			if (itemDTO.getUnitPrice() == null || itemDTO.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
				throw new RuntimeException("Unit price cannot be negative for product:" + product.getProductName());
			}

			BigDecimal tax = itemDTO.getTaxPercentage() == null ? BigDecimal.ZERO : itemDTO.getTaxPercentage();
			BigDecimal baseAmount = itemDTO.getUnitPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
			BigDecimal taxAmount = baseAmount.multiply(tax).divide(BigDecimal.valueOf(100));
			BigDecimal lineTotal = baseAmount.add(taxAmount);

			QuotationItem item = new QuotationItem();
			item.setProduct(product);
			item.setQuantity(itemDTO.getQuantity());
			item.setUnitPrice(itemDTO.getUnitPrice());
			item.setTaxPercentage(tax);
			item.setLineTotal(lineTotal);

			items.add(item);
			totalAmount = totalAmount.add(lineTotal);
		}
	}

	quotation.setQuotationItems(items);
	quotation.setTotalAmount(totalAmount);

	Quotation saved = quotationRepository.save(quotation);

	// ---- inline toResponseDTO logic using ModelMapper ----
	QuotationResponseDTO responseDTO = modelMapper.map(saved, QuotationResponseDTO.class);
	responseDTO.setCustomerName(saved.getCustomer().getCustomerName());
	responseDTO.setLeadRef(saved.getLead() != null ? saved.getLead().getLeadRef() : null);

	List<QuotationItemResponseDTO> itemDTOs = new ArrayList<>();
	if (saved.getQuotationItems() != null) {
		for (QuotationItem item : saved.getQuotationItems()) {
			QuotationItemResponseDTO itemDTO = modelMapper.map(item, QuotationItemResponseDTO.class);
			itemDTO.setProductName(item.getProduct().getProductName());
			itemDTOs.add(itemDTO);
		}
	}
	responseDTO.setQuotationItems(itemDTOs);

	return responseDTO;
}
	 */
}
