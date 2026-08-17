package com.example.entity;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.request.RegisterRequestDTO;
import com.example.dto.response.OpeningStockResponseDTO;
import com.example.dto.response.StockReportResponseDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="appuser")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	/*
	 @Override
	public boolean register(RegisterRequestDTO requestDTO) {
		if (usersRepository.existsByUsername(requestDTO.getUsername())) {
			return false;
		}
		Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() ->
                    new RuntimeException("USER role not found"));
		Users user = new Users();
		user.setUsername(requestDTO.getUsername());
		user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
		//user.setRole("USER");
		user.setRole(userRole);
		usersRepository.save(user);
		return true;
	}
	@Entity
@Table(name="roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	public interface RoleRepository extends JpaRepository<Role,Long>{
	Optional<Role> findByName(String name);
	
	/---------------
	 boolean changeRole(Long userId, Long roleId);
	 @Override
public boolean changeRole(Long userId, Long roleId) {

    Users user = usersRepository.findById(userId)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    Role role = roleRepository.findById(roleId)
            .orElseThrow(() ->
                    new RuntimeException("Role not found"));

    user.setRole(role);

    usersRepository.save(user);

    return true;
}
@PutMapping("/admin/users/{userId}/role")
public ResponseEntity<String> changeRole(
        @PathVariable Long userId,
        @RequestBody ChangeRoleRequestDTO requestDTO) {

    boolean updated = authService.changeRole(
            userId,
            requestDTO.getRoleId()
    );

    if (updated) {
        return ResponseEntity.ok("Role updated successfully");
    }

    return ResponseEntity.badRequest()
            .body("Role update failed");
}
package com.example.dto;

public class ChangeRoleRequestDTO {

    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
PUT /api/admin/users/{userId}/role
 OpeningStockResponseDTO getClosingStock(Long productId, Long warehouseId, LocalDate toDate);
    StockReportResponseDTO getStockReport(Long productId, Long warehouseId,
            LocalDate fromDate, LocalDate toDate);
             @Override
	    public OpeningStockResponseDTO getClosingStock(Long productId, Long warehouseId, LocalDate toDate) {

	        Integer result = openingStockRepository.getClosingStock(productId, warehouseId, toDate);

	        int closingStock;
	        if (result != null) {
	            closingStock = result;
	        } else {
	            closingStock = 0;
	        }

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found"));
	        Warehouse warehouse = warehouseRepository.findById(warehouseId)
	                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

	        OpeningStockResponseDTO response = new OpeningStockResponseDTO();
	        response.setProductName(product.getProductName());
	        response.setWarehouseName(warehouse.getWarehouseName());
	        response.setQuantity(closingStock);
	        response.setOpeningDate(toDate);

	        return response;
	    }

	    

	        @Override
	        public StockReportResponseDTO getStockReport(Long productId, Long warehouseId,
	                                                       LocalDate fromDate, LocalDate toDate) {

	            Integer openingResult = openingStockRepository.getOpeningQuantity(productId, warehouseId, fromDate);
	            int openingQuantity;
	            if (openingResult != null) {
	                openingQuantity = openingResult;
	            } else {
	                openingQuantity = 0;
	            }

	            Integer periodResult = openingStockRepository.getPeriodQuantity(productId, warehouseId, fromDate, toDate);
	            int periodQuantity;
	            if (periodResult != null) {
	                periodQuantity = periodResult;
	            } else {
	                periodQuantity = 0;
	            }

	            int closingQuantity = openingQuantity + periodQuantity;

	            Product product = productRepository.findById(productId)
	                    .orElseThrow(() -> new RuntimeException("Product not found"));
	            Warehouse warehouse = warehouseRepository.findById(warehouseId)
	                    .orElseThrow(() -> new RuntimeException("Warehouse not found"));

	            StockReportResponseDTO response = new StockReportResponseDTO();
	            response.setProductId(productId);
	            response.setProductName(product.getProductName());
	            response.setWarehouseId(warehouseId);
	            response.setWarehouseName(warehouse.getWarehouseName());
	            response.setFromDate(fromDate);
	            response.setToDate(toDate);
	            response.setOpeningQuantity(openingQuantity);
	            response.setPeriodQuantity(periodQuantity);
	            response.setClosingQuantity(closingQuantity);

	            return response;
	        }
	    }
	
@Query("SELECT SUM(o.quantity) " +
	           "FROM OpeningStock o " +
	           "WHERE o.product.productId = :productId " +
	           "AND o.warehouse.warehouseId = :warehouseId " +
	           "AND o.openingDate <= :toDate")
	    Integer getClosingStock(@Param("productId") Long productId,
	                             @Param("warehouseId") Long warehouseId,
	                             @Param("toDate") LocalDate toDate);
	@Query("SELECT SUM(o.quantity) " +
	           "FROM OpeningStock o " +
	           "WHERE o.product.productId = :productId " +
	           "AND o.warehouse.warehouseId = :warehouseId " +
	           "AND o.openingDate < :fromDate")
	    Integer getOpeningQuantity(@Param("productId") Long productId,
	                                @Param("warehouseId") Long warehouseId,
	                                @Param("fromDate") LocalDate fromDate);

	    // Period quantity: everything DURING the period (inclusive both ends)
	    @Query("SELECT SUM(o.quantity) " +
	           "FROM OpeningStock o " +
	           "WHERE o.product.productId = :productId " +
	           "AND o.warehouse.warehouseId = :warehouseId " +
	           "AND o.openingDate BETWEEN :fromDate AND :toDate")
	    Integer getPeriodQuantity(@Param("productId") Long productId,
	                               @Param("warehouseId") Long warehouseId,
	                               @Param("fromDate") LocalDate fromDate,
	                               @Param("toDate") LocalDate toDate);
}
@GetMapping("/closingstock")
    public ResponseEntity<OpeningStockResponseDTO> getClosingStock(
            @RequestParam Long productId,
            @RequestParam Long warehouseId,
            @RequestParam LocalDate toDate) {

        OpeningStockResponseDTO response = openingStockService.getClosingStock(productId, warehouseId, toDate);
        return ResponseEntity.ok(response);
    }
	@GetMapping("/stock-report")
    public ResponseEntity<StockReportResponseDTO> getStockReport(
            @RequestParam Long productId,
            @RequestParam Long warehouseId,
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) {

        StockReportResponseDTO response = openingStockService.getStockReport(productId, warehouseId, fromDate, toDate);
        return ResponseEntity.ok(response);
    }
    package com.example.dto.response;



import java.time.LocalDate;

public class StockReportResponseDTO {

	private Long productId;
	private String productName;
	private Long warehouseId;
	private String warehouseName;
	private LocalDate fromDate;
	private LocalDate toDate;
	private Integer openingQuantity;
	private Integer periodQuantity;
	private Integer closingQuantity;

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
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
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public Integer getOpeningQuantity() {
		return openingQuantity;
	}
	public void setOpeningQuantity(Integer openingQuantity) {
		this.openingQuantity = openingQuantity;
	}
	public Integer getPeriodQuantity() {
		return periodQuantity;
	}
	public void setPeriodQuantity(Integer periodQuantity) {
		this.periodQuantity = periodQuantity;
	}
	public Integer getClosingQuantity() {
		return closingQuantity;
	}
	public void setClosingQuantity(Integer closingQuantity) {
		this.closingQuantity = closingQuantity;
	}
}
	 */
}