package com.example.dto.response;




public class SubContactResponseDTO {
	private Long subContactId;
//    private CustomerResponseDTO customer;
	private String contactPersonName;
    private String contactPhone;
    private String contactEmail;
	
	public Long getSubContactId() {
		return subContactId;
	}
	public void setSubContactId(Long subContactId) {
		this.subContactId = subContactId;
	}
	
//	public CustomerResponseDTO getCustomer() {
//		return customer;
//	}
//	public void setCustomer(CustomerResponseDTO customer) {
//		this.customer = customer;
//	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
    
    
}
