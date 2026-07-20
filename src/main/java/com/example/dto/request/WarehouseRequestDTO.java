package com.example.dto.request;

import com.example.enums.WarehouseType;



public class WarehouseRequestDTO {
	private String warehouseName;
	private String location;
	private WarehouseType type;
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
