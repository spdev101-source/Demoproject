package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="subcontatcts")
public class SubContact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subContactId;
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	private String contactPersonName;
    private String contactPhone;
    private String contactEmail;
	
	public Long getSubContactId() {
		return subContactId;
	}
	public void setSubContactId(Long subContactId) {
		this.subContactId = subContactId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
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
