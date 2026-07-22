package com.example.dto.response;

public class SubContactResponseDTO {

	private Long subContactId;
	private String customerName; // filled manually in service, not by ModelMapper
	private String contactPersonName;
	private String contactPhone;
	private String contactEmail;

	public Long getSubContactId() { return subContactId; }
	public void setSubContactId(Long subContactId) { this.subContactId = subContactId; }
	public String getCustomerName() { return customerName; }
	public void setCustomerName(String customerName) { this.customerName = customerName; }
	public String getContactPersonName() { return contactPersonName; }
	public void setContactPersonName(String contactPersonName) { this.contactPersonName = contactPersonName; }
	public String getContactPhone() { return contactPhone; }
	public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
	public String getContactEmail() { return contactEmail; }
	public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}