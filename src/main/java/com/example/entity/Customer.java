package com.example.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;

	@Column(nullable = false)
	private String customerName;

	private String customerPhone;

	@Column(nullable = false)
	private String customerEmail;

	private String city;
	private String country;
	private String state;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private List<SubContact> subContacts = new ArrayList<>();

	public Long getCustomerId() { return customerId; }
	public void setCustomerId(Long customerId) { this.customerId = customerId; }
	public String getCustomerName() { return customerName; }
	public void setCustomerName(String customerName) { this.customerName = customerName; }
	public String getCustomerPhone() { return customerPhone; }
	public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
	public String getCustomerEmail() { return customerEmail; }
	public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }
	public String getCountry() { return country; }
	public void setCountry(String country) { this.country = country; }
	public String getState() { return state; }
	public void setState(String state) { this.state = state; }
	public List<SubContact> getSubContacts() { return subContacts; }
	public void setSubContacts(List<SubContact> subContacts) { this.subContacts = subContacts; }
}