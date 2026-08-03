package com.example.dto.response;

import java.time.LocalDate;

public class OpeningStockResponseDTO {

	private Long openingStockId;
	private String productName;
	private String warehouseName;
	private Integer quantity;
	private LocalDate openingDate;

	public Long getOpeningStockId() {
		return openingStockId;
	}
	public void setOpeningStockId(Long openingStockId) {
		this.openingStockId = openingStockId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
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