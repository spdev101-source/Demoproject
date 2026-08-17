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