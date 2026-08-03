package com.example.dto.request;

import com.example.enums.EnquiryStatus;

import java.time.LocalDate;

public class EnquiryRequestDTO {

	private Long customerId;
	private LocalDate date;
	private Long productId; // optional — can be null
	private EnquiryStatus status;

	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public EnquiryStatus getStatus() {
		return status;
	}
	public void setStatus(EnquiryStatus status) {
		this.status = status;
	}
}