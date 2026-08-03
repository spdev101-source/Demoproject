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