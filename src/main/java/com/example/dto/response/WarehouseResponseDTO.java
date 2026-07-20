package com.example.dto.response;

import com.example.enums.WarehouseType;



public class WarehouseResponseDTO {

	private Long warehouseId;
	private String warehouseName;
	private String location;
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
	
}
