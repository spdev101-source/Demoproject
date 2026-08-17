package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.dto.response.StockReportResponseDTO;
import com.example.entity.OpeningStock;

public interface OpeningStockRepository extends JpaRepository<OpeningStock, Long> {
	boolean existsByProductProductIdAndWarehouseWarehouseIdAndOpeningDate(
	        Long productId, Long warehouseId, LocalDate openingDate);
	boolean existsByProductProductIdAndWarehouseWarehouseIdAndOpeningDateAndOpeningStockIdNot(
	        Long productId, Long warehouseId, LocalDate openingDate, Long openingStockId);
	@Query("SELECT o FROM OpeningStock o WHERE o.product.productName LIKE CONCAT('%', :search, '%') " +
	       "OR o.warehouse.warehouseName LIKE CONCAT('%', :search, '%')")
	Page<OpeningStock> searchOpeningStocks(@Param("search") String search, Pageable pageable);
	@Query("SELECT o FROM OpeningStock o " +
		       "WHERE o.product.productId = :productId " +
		       "AND o.warehouse.warehouseId = :warehouseId " +
		       "AND o.openingDate <= :toDate " +
		       "ORDER BY o.openingDate DESC")
		List<OpeningStock> findClosingStockCandidates(@Param("productId") Long productId,
		                                               @Param("warehouseId") Long warehouseId,
		                                               @Param("toDate") LocalDate toDate);
//	@Query("SELECT new com.example.dto.response.StockReportResponseDTO(" +
//		       "SUM(CASE WHEN o.openingDate < :fromDate THEN o.quantity ELSE 0 END), " +
//		       "SUM(CASE WHEN o.openingDate >= :fromDate AND o.openingDate <= :toDate THEN o.quantity ELSE 0 END)) " +
//		       "FROM OpeningStock o " +
//		       "WHERE o.product.productId = :productId " +
//		       "AND o.warehouse.warehouseId = :warehouseId")
//		StockReportResponseDTO getStockReport(@Param("productId") Long productId,
//		                                       @Param("warehouseId") Long warehouseId,
//		                                       @Param("fromDate") LocalDate fromDate,
//		                                       @Param("toDate") LocalDate toDate);
	//----------------------------------------------
	//----------------------------------------
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