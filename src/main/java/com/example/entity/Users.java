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
	 @Query(value = "SELECT COALESCE(SUM(quantity), 0) " +
               "FROM opening_stock " +
               "WHERE product_id = :productId " +
               "AND warehouse_id = :warehouseId " +
               "AND opening_date < :fromDate",
       nativeQuery = true)
Integer getOpeningQuantity(@Param("productId") Long productId,
                            @Param("warehouseId") Long warehouseId,
                            @Param("fromDate") LocalDate fromDate);
                            
                            
   CREATE OR REPLACE PROCEDURE get_opening_quantity(
    IN p_product_id BIGINT,
    IN p_warehouse_id BIGINT,
    IN p_from_date DATE,
    OUT p_opening_quantity INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT COALESCE(SUM(quantity), 0)
    INTO p_opening_quantity
    FROM opening_stock
    WHERE product_id = p_product_id
      AND warehouse_id = p_warehouse_id
      AND opening_date < p_from_date;
END;
$$;

CALL get_opening_quantity(1, 1, '2026-08-03', NULL);

@Query(value = "CALL get_opening_quantity(:productId, :warehouseId, :fromDate, NULL)", nativeQuery = true)
Integer getOpeningQuantity(@Param("productId") Long productId,
                            @Param("warehouseId") Long warehouseId,
                            @Param("fromDate") LocalDate fromDate);
                            
   @Query(value = "SELECT COALESCE(SUM(quantity), 0) " +
               "FROM opening_stock " +
               "WHERE product_id = :productId " +
               "AND warehouse_id = :warehouseId " +
               "AND opening_date BETWEEN :fromDate AND :toDate",
       nativeQuery = true)
Integer getPeriodQuantity(@Param("productId") Long productId,
                           @Param("warehouseId") Long warehouseId,
                           @Param("fromDate") LocalDate fromDate,
                           @Param("toDate") LocalDate toDate);
                           
       CREATE OR REPLACE PROCEDURE get_period_quantity(
    IN p_product_id BIGINT,
    IN p_warehouse_id BIGINT,
    IN p_from_date DATE,
    IN p_to_date DATE,
    OUT p_period_quantity INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT COALESCE(SUM(quantity), 0)
    INTO p_period_quantity
    FROM opening_stock
    WHERE product_id = p_product_id
      AND warehouse_id = p_warehouse_id
      AND opening_date BETWEEN p_from_date AND p_to_date;
END;
$$;

CALL get_period_quantity(1, 1, '2026-08-03', '2026-08-10', NULL);

@Query(value = "CALL get_period_quantity(:productId, :warehouseId, :fromDate, :toDate, NULL)", nativeQuery = true)
Integer getPeriodQuantity(@Param("productId") Long productId,
                           @Param("warehouseId") Long warehouseId,
                           @Param("fromDate") LocalDate fromDate,
                           @Param("toDate") LocalDate toDate);
                           
                           
     CREATE OR REPLACE PROCEDURE get_opening_and_period_quantity(
    IN p_product_id BIGINT,
    IN p_warehouse_id BIGINT,
    IN p_from_date DATE,
    IN p_to_date DATE,
    OUT p_opening_quantity INTEGER,
    OUT p_period_quantity INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT
        COALESCE(SUM(CASE WHEN opening_date < p_from_date THEN quantity ELSE 0 END), 0),
        COALESCE(SUM(CASE WHEN opening_date BETWEEN p_from_date AND p_to_date THEN quantity ELSE 0 END), 0)
    INTO
        p_opening_quantity,
        p_period_quantity
    FROM opening_stock
    WHERE product_id = p_product_id
      AND warehouse_id = p_warehouse_id
      AND opening_date <= p_to_date;
END;
$$;
CALL get_opening_and_period_quantity(1, 1, '2026-08-03', '2026-08-10', NULL, NULL);

public interface OpeningPeriodProjection {
    Integer getOpeningQuantity();
    Integer getPeriodQuantity();
}
@Query(value = "SELECT " +
               "COALESCE(SUM(CASE WHEN opening_date < :fromDate THEN quantity ELSE 0 END), 0) AS opening_quantity, " +
               "COALESCE(SUM(CASE WHEN opening_date BETWEEN :fromDate AND :toDate THEN quantity ELSE 0 END), 0) AS period_quantity " +
               "FROM opening_stock " +
               "WHERE product_id = :productId " +
               "AND warehouse_id = :warehouseId " +
               "AND opening_date <= :toDate",
       nativeQuery = true)
OpeningPeriodProjection getOpeningAndPeriodQuantity(@Param("productId") Long productId,
                                                      @Param("warehouseId") Long warehouseId,
                                                      @Param("fromDate") LocalDate fromDate,
                                                      @Param("toDate") LocalDate toDate);
                                                      
        @Override
public StockReportResponseDTO getStockReport(Long productId, Long warehouseId,
                                               LocalDate fromDate, LocalDate toDate) {

    OpeningPeriodProjection result = openingStockRepository.getOpeningAndPeriodQuantity(
            productId, warehouseId, fromDate, toDate);

    int openingQuantity;
    if (result.getOpeningQuantity() != null) {
        openingQuantity = result.getOpeningQuantity();
    } else {
        openingQuantity = 0;
    }

    int periodQuantity;
    if (result.getPeriodQuantity() != null) {
        periodQuantity = result.getPeriodQuantity();
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
	 */
}