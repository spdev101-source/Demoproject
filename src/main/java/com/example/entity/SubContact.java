package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subcontacts")
public class SubContact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subContactId;

	private String contactPersonName;
	private String contactPhone;
	private String contactEmail;
	@Transient
	private Customer customer;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Long getSubContactId() { return subContactId; }
	public void setSubContactId(Long subContactId) { this.subContactId = subContactId; }
	public String getContactPersonName() { return contactPersonName; }
	public void setContactPersonName(String contactPersonName) { this.contactPersonName = contactPersonName; }
	public String getContactPhone() { return contactPhone; }
	public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
	public String getContactEmail() { return contactEmail; }
	public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}