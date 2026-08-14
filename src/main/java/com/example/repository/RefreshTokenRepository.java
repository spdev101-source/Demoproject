package com.example.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);
	void deleteByUsername(String username);
}
/*

public enum SalesOrderStatus {
	DRAFT,
	CONFIRMED,
	PARTIALLY_DELIVERED,
	CONVERTED
}
package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "uoms")
public class UOM {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uomId;

	@Column(nullable = false, unique = true)
	private String uomName;

	public Long getUomId() {
		return uomId;
	}
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
}
package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.UOM;

public interface UOMRepository extends JpaRepository<UOM, Long> {
	boolean existsByUomName(String uomName);
}
package com.example.dto.request;

public class UOMRequestDTO {

	private String uomName;
	private String uomCode;

	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public String getUomCode() {
		return uomCode;
	}
	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}
}
package com.example.dto.response;

public class UOMResponseDTO {

	private Long uomId;
	private String uomName;
	private String uomCode;

	public Long getUomId() {
		return uomId;
	}
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public String getUomCode() {
		return uomCode;
	}
	public void setUomCode(String uomCode) {
		this.uomCode = uomCode;
	}
}
package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.UOM;

public interface UOMRepository extends JpaRepository<UOM, Long> {

	@Query("SELECT u FROM UOM u WHERE u.uomName LIKE CONCAT('%', :search, '%')")
	Page<UOM> searchUOMs(@Param("search") String search, Pageable pageable);
}
package com.example.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.UOMRequestDTO;
import com.example.dto.response.UOMResponseDTO;

public interface UOMService {
	UOMResponseDTO createUOM(UOMRequestDTO requestDTO);
	Optional<UOMResponseDTO> getUOMById(Long uomId);
	UOMResponseDTO updateUOM(Long uomId, UOMRequestDTO requestDTO);
	boolean deleteUOM(Long uomId);
	Page<UOMResponseDTO> getAllUOMs(int page, int size, String sortBy, String direction);
	Page<UOMResponseDTO> searchUOMs(String search, int page, int size, String sortBy, String direction);
}
package com.example.serviceimple;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.request.UOMRequestDTO;
import com.example.dto.response.UOMResponseDTO;
import com.example.entity.UOM;
import com.example.repository.UOMRepository;
import com.example.service.UOMService;

@Service
public class UOMServiceImple implements UOMService {

	private final UOMRepository uomRepository;
	private final ModelMapper modelMapper;

	public UOMServiceImple(UOMRepository uomRepository, ModelMapper modelMapper) {
		this.uomRepository = uomRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public UOMResponseDTO createUOM(UOMRequestDTO requestDTO) {
		UOM uom = new UOM();
		uom.setUomName(requestDTO.getUomName());
		uom.setUomCode(requestDTO.getUomCode());

		UOM saved = uomRepository.save(uom);
		return modelMapper.map(saved, UOMResponseDTO.class);
	}

	@Override
	public Optional<UOMResponseDTO> getUOMById(Long uomId) {
		return uomRepository.findById(uomId).map(uom -> modelMapper.map(uom, UOMResponseDTO.class));
	}

	@Override
	public UOMResponseDTO updateUOM(Long uomId, UOMRequestDTO requestDTO) {
		UOM uom = uomRepository.findById(uomId)
				.orElseThrow(() -> new RuntimeException("UOM Not found with id:" + uomId));

		uom.setUomName(requestDTO.getUomName());
		uom.setUomCode(requestDTO.getUomCode());

		UOM updated = uomRepository.save(uom);
		return modelMapper.map(updated, UOMResponseDTO.class);
	}

	@Override
	public boolean deleteUOM(Long uomId) {
		if (uomRepository.existsById(uomId)) {
			uomRepository.deleteById(uomId);
			return true;
		}
		return false;
	}

	@Override
	public Page<UOMResponseDTO> getAllUOMs(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return uomRepository.findAll(pageable).map(uom -> modelMapper.map(uom, UOMResponseDTO.class));
	}

	@Override
	public Page<UOMResponseDTO> searchUOMs(String search, int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return uomRepository.searchUOMs(search, pageable).map(uom -> modelMapper.map(uom, UOMResponseDTO.class));
	}
}
package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.UOMRequestDTO;
import com.example.dto.response.UOMResponseDTO;
import com.example.service.UOMService;

@RestController
@RequestMapping("/api/uoms")
public class UOMController {

	private final UOMService uomService;

	public UOMController(UOMService uomService) {
		this.uomService = uomService;
	}

	@PostMapping("/save")
	public ResponseEntity<UOMResponseDTO> createUOM(@RequestBody UOMRequestDTO requestDTO) {
		UOMResponseDTO saved = uomService.createUOM(requestDTO);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{uomId}")
	public ResponseEntity<UOMResponseDTO> getUOMById(@PathVariable Long uomId) {
		UOMResponseDTO uom = uomService.getUOMById(uomId)
				.orElseThrow(() -> new RuntimeException("UOM Not found with id:" + uomId));
		return new ResponseEntity<>(uom, HttpStatus.OK);
	}

	@PutMapping("/update/{uomId}")
	public UOMResponseDTO updateUOM(@PathVariable Long uomId, @RequestBody UOMRequestDTO requestDTO) {
		return uomService.updateUOM(uomId, requestDTO);
	}

	@DeleteMapping("/{uomId}")
	public ResponseEntity<String> deleteUOM(@PathVariable Long uomId) {
		boolean deleted = uomService.deleteUOM(uomId);
		if (!deleted) {
			return new ResponseEntity<>("UOM not found with id:" + uomId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("UOM deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public Page<UOMResponseDTO> getAllUOMs(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "uomId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return uomService.getAllUOMs(page, size, sortBy, direction);
	}

	@GetMapping("/searching")
	public Page<UOMResponseDTO> searchUOMs(
			@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "uomId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return uomService.searchUOMs(search, page, size, sortBy, direction);
	}
}
package com.example.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.enums.SalesOrderStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "sales_orders")
public class SalesOrder extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long salesOrderId;

	private LocalDate orderDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	@Enumerated(EnumType.STRING)
	private SalesOrderStatus status;

	private BigDecimal totalAmount;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "sales_order_id")
	private List<SalesOrderItem> salesOrderItems;

	public Long getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public SalesOrderStatus getStatus() {
		return status;
	}
	public void setStatus(SalesOrderStatus status) {
		this.status = status;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<SalesOrderItem> getSalesOrderItems() {
		return salesOrderItems;
	}
	public void setSalesOrderItems(List<SalesOrderItem> salesOrderItems) {
		this.salesOrderItems = salesOrderItems;
	}
}
package com.example.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "sales_order_items")
public class SalesOrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long salesItemId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uom_id")
	private UOM uom;

	private Integer quantity;
	private BigDecimal lineTotal;

	public Long getSalesItemId() {
		return salesItemId;
	}
	public void setSalesItemId(Long salesItemId) {
		this.salesItemId = salesItemId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public UOM getUom() {
		return uom;
	}
	public void setUom(UOM uom) {
		this.uom = uom;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

import com.example.enums.SalesOrderStatus;

public class SalesOrderRequestDTO {

	private LocalDate orderDate;
	private Long customerId;
	private Long warehouseId;
	private SalesOrderStatus status;
	private List<SalesOrderItemRequestDTO> salesOrderItems;

	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public SalesOrderStatus getStatus() {
		return status;
	}
	public void setStatus(SalesOrderStatus status) {
		this.status = status;
	}
	public List<SalesOrderItemRequestDTO> getSalesOrderItems() {
		return salesOrderItems;
	}
	public void setSalesOrderItems(List<SalesOrderItemRequestDTO> salesOrderItems) {
		this.salesOrderItems = salesOrderItems;
	}
}
package com.example.dto.request;

import java.math.BigDecimal;

public class SalesOrderItemRequestDTO {

	private Long productId;
	private Long uomId;
	private Integer quantity;
	private BigDecimal lineTotal;

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getUomId() {
		return uomId;
	}
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}
}
package com.example.dto.response;

import java.math.BigDecimal;

public class SalesOrderItemResponseDTO {

	private Long salesItemId;
	private String productName;
	private String uomName;
	private Integer quantity;
	private BigDecimal lineTotal;

	public Long getSalesItemId() {
		return salesItemId;
	}
	public void setSalesItemId(Long salesItemId) {
		this.salesItemId = salesItemId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}
}
package com.example.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.enums.SalesOrderStatus;

public class SalesOrderResponseDTO {

	private Long salesOrderId;
	private LocalDate orderDate;
	private String customerName;
	private String warehouseName;
	private SalesOrderStatus status;
	private BigDecimal totalAmount;
	private List<SalesOrderItemResponseDTO> salesOrderItems;

	public Long getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public SalesOrderStatus getStatus() {
		return status;
	}
	public void setStatus(SalesOrderStatus status) {
		this.status = status;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<SalesOrderItemResponseDTO> getSalesOrderItems() {
		return salesOrderItems;
	}
	public void setSalesOrderItems(List<SalesOrderItemResponseDTO> salesOrderItems) {
		this.salesOrderItems = salesOrderItems;
	}
}
package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.SalesOrder;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {

	@Query("SELECT s FROM SalesOrder s WHERE s.customer.customerName LIKE CONCAT('%', :search, '%')")
	Page<SalesOrder> searchSalesOrders(@Param("search") String search, Pageable pageable);
}
package com.example.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.SalesOrderRequestDTO;
import com.example.dto.response.SalesOrderResponseDTO;
import com.example.enums.SalesOrderStatus;

public interface SalesOrderService {
	SalesOrderResponseDTO createSalesOrder(SalesOrderRequestDTO requestDTO);
	Optional<SalesOrderResponseDTO> getSalesOrderById(Long salesOrderId);
	SalesOrderResponseDTO updateSalesOrder(Long salesOrderId, SalesOrderRequestDTO requestDTO);
	boolean deleteSalesOrder(Long salesOrderId);
	Page<SalesOrderResponseDTO> getAllSalesOrders(int page, int size, String sortBy, String direction);
	Page<SalesOrderResponseDTO> searchSalesOrders(String search, int page, int size, String sortBy, String direction);
	SalesOrderResponseDTO changeStatus(Long salesOrderId, SalesOrderStatus newStatus);
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

import com.example.dto.request.SalesOrderItemRequestDTO;
import com.example.dto.request.SalesOrderRequestDTO;
import com.example.dto.response.SalesOrderItemResponseDTO;
import com.example.dto.response.SalesOrderResponseDTO;
import com.example.entity.Customer;
import com.example.entity.Product;
import com.example.entity.SalesOrder;
import com.example.entity.SalesOrderItem;
import com.example.entity.UOM;
import com.example.entity.Warehouse;
import com.example.enums.SalesOrderStatus;
import com.example.repository.CustomerRepository;
import com.example.repository.ProductRepository;
import com.example.repository.SalesOrderRepository;
import com.example.repository.UOMRepository;
import com.example.repository.WarehouseRepository;
import com.example.service.SalesOrderService;

@Service
public class SalesOrderServiceImple implements SalesOrderService {

	private final SalesOrderRepository salesOrderRepository;
	private final CustomerRepository customerRepository;
	private final WarehouseRepository warehouseRepository;
	private final ProductRepository productRepository;
	private final UOMRepository uomRepository;
	private final ModelMapper modelMapper;

	public SalesOrderServiceImple(SalesOrderRepository salesOrderRepository, CustomerRepository customerRepository,
			WarehouseRepository warehouseRepository, ProductRepository productRepository,
			UOMRepository uomRepository, ModelMapper modelMapper) {
		this.salesOrderRepository = salesOrderRepository;
		this.customerRepository = customerRepository;
		this.warehouseRepository = warehouseRepository;
		this.productRepository = productRepository;
		this.uomRepository = uomRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public SalesOrderResponseDTO createSalesOrder(SalesOrderRequestDTO requestDTO) {
		Customer customer = customerRepository.findById(requestDTO.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));
		Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
				.orElseThrow(() -> new RuntimeException("Warehouse Not found with id:" + requestDTO.getWarehouseId()));

		if (requestDTO.getOrderDate() == null || requestDTO.getOrderDate().isAfter(LocalDate.now())) {
			throw new RuntimeException("Order date cannot be in the future");
		}

		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setOrderDate(requestDTO.getOrderDate());
		salesOrder.setCustomer(customer);
		salesOrder.setWarehouse(warehouse);
		salesOrder.setStatus(requestDTO.getStatus() == null ? SalesOrderStatus.DRAFT : requestDTO.getStatus());

		List<SalesOrderItem> items = new ArrayList<>();
		BigDecimal totalAmount = BigDecimal.ZERO;

		if (requestDTO.getSalesOrderItems() != null) {
			for (SalesOrderItemRequestDTO itemDTO : requestDTO.getSalesOrderItems()) {
				Product product = productRepository.findById(itemDTO.getProductId())
						.orElseThrow(() -> new RuntimeException("Product Not found with id:" + itemDTO.getProductId()));
				UOM uom = uomRepository.findById(itemDTO.getUomId())
						.orElseThrow(() -> new RuntimeException("UOM Not found with id:" + itemDTO.getUomId()));

				if (itemDTO.getQuantity() == null || itemDTO.getQuantity() <= 0) {
					throw new RuntimeException("Quantity must be greater than 0 for product:" + product.getProductName());
				}
				if (itemDTO.getLineTotal() == null || itemDTO.getLineTotal().compareTo(BigDecimal.ZERO) < 0) {
					throw new RuntimeException("Line total cannot be negative for product:" + product.getProductName());
				}

				SalesOrderItem item = new SalesOrderItem();
				item.setProduct(product);
				item.setUom(uom);
				item.setQuantity(itemDTO.getQuantity());
				item.setLineTotal(itemDTO.getLineTotal());

				items.add(item);
				totalAmount = totalAmount.add(itemDTO.getLineTotal());
			}
		}

		salesOrder.setSalesOrderItems(items);
		salesOrder.setTotalAmount(totalAmount);

		SalesOrder saved = salesOrderRepository.save(salesOrder);
		return toResponseDTO(saved);
	}

	@Override
	public Optional<SalesOrderResponseDTO> getSalesOrderById(Long salesOrderId) {
		return salesOrderRepository.findById(salesOrderId).map(this::toResponseDTO);
	}

	@Override
	public SalesOrderResponseDTO updateSalesOrder(Long salesOrderId, SalesOrderRequestDTO requestDTO) {
		SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId)
				.orElseThrow(() -> new RuntimeException("Sales order Not found with id:" + salesOrderId));

		if (salesOrder.getStatus() == SalesOrderStatus.CONVERTED) {
			throw new RuntimeException("Cannot edit a sales order that is already Converted");
		}

		Customer customer = customerRepository.findById(requestDTO.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));
		Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
				.orElseThrow(() -> new RuntimeException("Warehouse Not found with id:" + requestDTO.getWarehouseId()));

		if (requestDTO.getOrderDate() == null || requestDTO.getOrderDate().isAfter(LocalDate.now())) {
			throw new RuntimeException("Order date cannot be in the future");
		}

		salesOrder.setOrderDate(requestDTO.getOrderDate());
		salesOrder.setCustomer(customer);
		salesOrder.setWarehouse(warehouse);

		if (salesOrder.getSalesOrderItems() != null) {
			salesOrder.getSalesOrderItems().clear();
		} else {
			salesOrder.setSalesOrderItems(new ArrayList<>());
		}

		BigDecimal totalAmount = BigDecimal.ZERO;
		if (requestDTO.getSalesOrderItems() != null) {
			for (SalesOrderItemRequestDTO itemDTO : requestDTO.getSalesOrderItems()) {
				Product product = productRepository.findById(itemDTO.getProductId())
						.orElseThrow(() -> new RuntimeException("Product Not found with id:" + itemDTO.getProductId()));
				UOM uom = uomRepository.findById(itemDTO.getUomId())
						.orElseThrow(() -> new RuntimeException("UOM Not found with id:" + itemDTO.getUomId()));

				if (itemDTO.getQuantity() == null || itemDTO.getQuantity() <= 0) {
					throw new RuntimeException("Quantity must be greater than 0 for product:" + product.getProductName());
				}
				if (itemDTO.getLineTotal() == null || itemDTO.getLineTotal().compareTo(BigDecimal.ZERO) < 0) {
					throw new RuntimeException("Line total cannot be negative for product:" + product.getProductName());
				}

				SalesOrderItem item = new SalesOrderItem();
				item.setProduct(product);
				item.setUom(uom);
				item.setQuantity(itemDTO.getQuantity());
				item.setLineTotal(itemDTO.getLineTotal());

				salesOrder.getSalesOrderItems().add(item);
				totalAmount = totalAmount.add(itemDTO.getLineTotal());
			}
		}

		salesOrder.setTotalAmount(totalAmount);

		SalesOrder updated = salesOrderRepository.save(salesOrder);
		return toResponseDTO(updated);
	}

	@Override
	public boolean deleteSalesOrder(Long salesOrderId) {
		if (salesOrderRepository.existsById(salesOrderId)) {
			salesOrderRepository.deleteById(salesOrderId);
			return true;
		}
		return false;
	}

	@Override
	public Page<SalesOrderResponseDTO> getAllSalesOrders(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return salesOrderRepository.findAll(pageable).map(this::toResponseDTO);
	}

	@Override
	public Page<SalesOrderResponseDTO> searchSalesOrders(String search, int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return salesOrderRepository.searchSalesOrders(search, pageable).map(this::toResponseDTO);
	}

	@Override
	public SalesOrderResponseDTO changeStatus(Long salesOrderId, SalesOrderStatus newStatus) {
		SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId)
				.orElseThrow(() -> new RuntimeException("Sales order Not found with id:" + salesOrderId));

		if (salesOrder.getStatus() == SalesOrderStatus.CONVERTED) {
			throw new RuntimeException("Cannot change status of a sales order that is already Converted");
		}

		salesOrder.setStatus(newStatus);
		SalesOrder updated = salesOrderRepository.save(salesOrder);
		return toResponseDTO(updated);
	}

	private SalesOrderResponseDTO toResponseDTO(SalesOrder salesOrder) {
		SalesOrderResponseDTO dto = modelMapper.map(salesOrder, SalesOrderResponseDTO.class);
		dto.setCustomerName(salesOrder.getCustomer().getCustomerName());
		dto.setWarehouseName(salesOrder.getWarehouse().getWarehouseName());

		List<SalesOrderItemResponseDTO> itemDTOs = new ArrayList<>();
		if (salesOrder.getSalesOrderItems() != null) {
			for (SalesOrderItem item : salesOrder.getSalesOrderItems()) {
				SalesOrderItemResponseDTO itemDTO = modelMapper.map(item, SalesOrderItemResponseDTO.class);
				itemDTO.setProductName(item.getProduct().getProductName());
				itemDTO.setUomName(item.getUom().getUomName());
				itemDTOs.add(itemDTO);
			}
		}
		dto.setSalesOrderItems(itemDTOs);
		return dto;
	}
}
package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.SalesOrderRequestDTO;
import com.example.dto.response.SalesOrderResponseDTO;
import com.example.enums.SalesOrderStatus;
import com.example.service.SalesOrderService;

@RestController
@RequestMapping("/api/sales-orders")
public class SalesOrderController {

	private final SalesOrderService salesOrderService;

	public SalesOrderController(SalesOrderService salesOrderService) {
		this.salesOrderService = salesOrderService;
	}

	@PostMapping("/save")
	public ResponseEntity<SalesOrderResponseDTO> createSalesOrder(@RequestBody SalesOrderRequestDTO requestDTO) {
		SalesOrderResponseDTO saved = salesOrderService.createSalesOrder(requestDTO);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{salesOrderId}")
	public ResponseEntity<SalesOrderResponseDTO> getSalesOrderById(@PathVariable Long salesOrderId) {
		SalesOrderResponseDTO salesOrder = salesOrderService.getSalesOrderById(salesOrderId)
				.orElseThrow(() -> new RuntimeException("Sales order Not found with id:" + salesOrderId));
		return new ResponseEntity<>(salesOrder, HttpStatus.OK);
	}

	@PutMapping("/update/{salesOrderId}")
	public SalesOrderResponseDTO updateSalesOrder(@PathVariable Long salesOrderId, @RequestBody SalesOrderRequestDTO requestDTO) {
		return salesOrderService.updateSalesOrder(salesOrderId, requestDTO);
	}

	@DeleteMapping("/{salesOrderId}")
	public ResponseEntity<String> deleteSalesOrder(@PathVariable Long salesOrderId) {
		boolean deleted = salesOrderService.deleteSalesOrder(salesOrderId);
		if (!deleted) {
			return new ResponseEntity<>("Sales order not found with id:" + salesOrderId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Sales order deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public Page<SalesOrderResponseDTO> getAllSalesOrders(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "salesOrderId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return salesOrderService.getAllSalesOrders(page, size, sortBy, direction);
	}

	@GetMapping("/searching")
	public Page<SalesOrderResponseDTO> searchSalesOrders(
			@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "salesOrderId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return salesOrderService.searchSalesOrders(search, page, size, sortBy, direction);
	}

	@PutMapping("/status/{salesOrderId}")
	public SalesOrderResponseDTO changeStatus(@PathVariable Long salesOrderId, @RequestParam SalesOrderStatus status) {
		return salesOrderService.changeStatus(salesOrderId, status);
	}
}
@Override
public SalesOrderResponseDTO createSalesOrder(SalesOrderRequestDTO requestDTO) {
	Customer customer = customerRepository.findById(requestDTO.getCustomerId())
			.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));
	Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
			.orElseThrow(() -> new RuntimeException("Warehouse Not found with id:" + requestDTO.getWarehouseId()));

	SalesOrder salesOrder = new SalesOrder();
	salesOrder.setOrderDate(requestDTO.getOrderDate());
	salesOrder.setCustomer(customer);
	salesOrder.setWarehouse(warehouse);
	salesOrder.setStatus(requestDTO.getStatus() == null ? SalesOrderStatus.DRAFT : requestDTO.getStatus());

	List<SalesOrderItem> items = new ArrayList<>();

	if (requestDTO.getSalesOrderItems() != null) {
		for (SalesOrderItemRequestDTO itemDTO : requestDTO.getSalesOrderItems()) {
			Product product = productRepository.findById(itemDTO.getProductId())
					.orElseThrow(() -> new RuntimeException("Product Not found with id:" + itemDTO.getProductId()));
			UOM uom = uomRepository.findById(itemDTO.getUomId())
					.orElseThrow(() -> new RuntimeException("UOM Not found with id:" + itemDTO.getUomId()));

			SalesOrderItem item = new SalesOrderItem();
			item.setProduct(product);
			item.setUom(uom);
			item.setQuantity(itemDTO.getQuantity());
			item.setLineTotal(itemDTO.getLineTotal());

			items.add(item);
		}
	}

	salesOrder.setSalesOrderItems(items);

	SalesOrder saved = salesOrderRepository.save(salesOrder);

	// ---- inline toResponseDTO logic using ModelMapper ----
	SalesOrderResponseDTO responseDTO = modelMapper.map(saved, SalesOrderResponseDTO.class);
	responseDTO.setCustomerName(saved.getCustomer().getCustomerName());
	responseDTO.setWarehouseName(saved.getWarehouse().getWarehouseName());

	List<SalesOrderItemResponseDTO> itemDTOs = new ArrayList<>();
	if (saved.getSalesOrderItems() != null) {
		for (SalesOrderItem item : saved.getSalesOrderItems()) {
			SalesOrderItemResponseDTO itemDTO = modelMapper.map(item, SalesOrderItemResponseDTO.class);
			itemDTO.setProductName(item.getProduct().getProductName());
			itemDTO.setUomName(item.getUom().getUomName());
			itemDTOs.add(itemDTO);
		}
	}
	responseDTO.setSalesOrderItems(itemDTOs);

	return responseDTO;
}
@Override
public SalesOrderResponseDTO updateSalesOrder(Long salesOrderId, SalesOrderRequestDTO requestDTO) {
	SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId)
			.orElseThrow(() -> new RuntimeException("Sales order Not found with id:" + salesOrderId));

	if (salesOrder.getStatus() == SalesOrderStatus.CONVERTED) {
		throw new RuntimeException("Cannot edit a sales order that is already Converted");
	}

	Customer customer = customerRepository.findById(requestDTO.getCustomerId())
			.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));
	Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
			.orElseThrow(() -> new RuntimeException("Warehouse Not found with id:" + requestDTO.getWarehouseId()));

	salesOrder.setOrderDate(requestDTO.getOrderDate());
	salesOrder.setCustomer(customer);
	salesOrder.setWarehouse(warehouse);

	if (salesOrder.getSalesOrderItems() != null) {
		salesOrder.getSalesOrderItems().clear();
	} else {
		salesOrder.setSalesOrderItems(new ArrayList<>());
	}

	if (requestDTO.getSalesOrderItems() != null) {
		for (SalesOrderItemRequestDTO itemDTO : requestDTO.getSalesOrderItems()) {
			Product product = productRepository.findById(itemDTO.getProductId())
					.orElseThrow(() -> new RuntimeException("Product Not found with id:" + itemDTO.getProductId()));
			UOM uom = uomRepository.findById(itemDTO.getUomId())
					.orElseThrow(() -> new RuntimeException("UOM Not found with id:" + itemDTO.getUomId()));

			SalesOrderItem item = new SalesOrderItem();
			item.setProduct(product);
			item.setUom(uom);
			item.setQuantity(itemDTO.getQuantity());
			item.setLineTotal(itemDTO.getLineTotal());

			salesOrder.getSalesOrderItems().add(item);
		}
	}

	SalesOrder updated = salesOrderRepository.save(salesOrder);

	// ---- inline toResponseDTO logic using ModelMapper ----
	SalesOrderResponseDTO responseDTO = modelMapper.map(updated, SalesOrderResponseDTO.class);
	responseDTO.setCustomerName(updated.getCustomer().getCustomerName());
	responseDTO.setWarehouseName(updated.getWarehouse().getWarehouseName());

	List<SalesOrderItemResponseDTO> itemDTOs = new ArrayList<>();
	if (updated.getSalesOrderItems() != null) {
		for (SalesOrderItem item : updated.getSalesOrderItems()) {
			SalesOrderItemResponseDTO itemDTO = modelMapper.map(item, SalesOrderItemResponseDTO.class);
			itemDTO.setProductName(item.getProduct().getProductName());
			itemDTO.setUomName(item.getUom().getUomName());
			itemDTOs.add(itemDTO);
		}
	}
	responseDTO.setSalesOrderItems(itemDTOs);

	return responseDTO;
}
package com.example.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.example.enums.SalesOrderStatus;

public class SalesOrderRequestDTO {

	private Long customerId;
	private Long warehouseId;
	private LocalDate orderDate;
	private SalesOrderStatus status;
	private List<SalesOrderItemRequestDTO> salesOrderItems;

	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public SalesOrderStatus getStatus() {
		return status;
	}
	public void setStatus(SalesOrderStatus status) {
		this.status = status;
	}
	public List<SalesOrderItemRequestDTO> getSalesOrderItems() {
		return salesOrderItems;
	}
	public void setSalesOrderItems(List<SalesOrderItemRequestDTO> salesOrderItems) {
		this.salesOrderItems = salesOrderItems;
	}
}
package com.example.dto.request;

import java.math.BigDecimal;

public class SalesOrderItemRequestDTO {

	private Long productId;
	private Long uomId;
	private Integer quantity;
	private BigDecimal lineTotal;

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getUomId() {
		return uomId;
	}
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}
}
package com.example.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.enums.SalesOrderStatus;

public class SalesOrderResponseDTO {

	private Long salesOrderId;
	private String customerName;
	private String warehouseName;
	private LocalDate orderDate;
	private SalesOrderStatus status;
	private BigDecimal totalAmount;
	private List<SalesOrderItemResponseDTO> salesOrderItems;

	public Long getSalesOrderId() {
		return salesOrderId;
	}
	public void setSalesOrderId(Long salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public SalesOrderStatus getStatus() {
		return status;
	}
	public void setStatus(SalesOrderStatus status) {
		this.status = status;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public List<SalesOrderItemResponseDTO> getSalesOrderItems() {
		return salesOrderItems;
	}
	public void setSalesOrderItems(List<SalesOrderItemResponseDTO> salesOrderItems) {
		this.salesOrderItems = salesOrderItems;
	}
}
package com.example.dto.response;

import java.math.BigDecimal;

public class SalesOrderItemResponseDTO {

	private Long salesOrderItemId;
	private String productName;
	private String uomName;
	private Integer quantity;
	private BigDecimal lineTotal;

	public Long getSalesOrderItemId() {
		return salesOrderItemId;
	}
	public void setSalesOrderItemId(Long salesOrderItemId) {
		this.salesOrderItemId = salesOrderItemId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}
}
 */
