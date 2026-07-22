package com.example.dto.request;

public class SubContactRequestDTO {

	private Long customerId; // needed to know WHICH customer's list to add into
	private String contactPersonName;
	private String contactPhone;
	private String contactEmail;

	public Long getCustomerId() { return customerId; }
	public void setCustomerId(Long customerId) { this.customerId = customerId; }
	public String getContactPersonName() { return contactPersonName; }
	public void setContactPersonName(String contactPersonName) { this.contactPersonName = contactPersonName; }
	public String getContactPhone() { return contactPhone; }
	public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
	public String getContactEmail() { return contactEmail; }
	public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}