package com.example.dto.request;

import java.util.List;

public class CustomerRequestDTO {

	private String customerName;
	private String customerPhone;
	private String customerEmail;
	private String city;
	private String country;
	private String state;
	private List<SubContactRequestDTO> subContacts;

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<SubContactRequestDTO> getSubContacts() {
		return subContacts;
	}
	public void setSubContacts(List<SubContactRequestDTO> subContacts) {
		this.subContacts = subContacts;
	}
}