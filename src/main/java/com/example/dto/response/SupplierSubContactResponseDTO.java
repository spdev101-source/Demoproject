package com.example.dto.response;

public class SupplierSubContactResponseDTO {
    private Long subContactId;
    private String supplierName;
    private String contactPersonName;
    private String contactPhone;
    private String contactEmail;

    public SupplierSubContactResponseDTO() {}

    public Long getSubContactId() { return subContactId; }
    public void setSubContactId(Long subContactId) { this.subContactId = subContactId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getContactPersonName() { return contactPersonName; }
    public void setContactPersonName(String contactPersonName) { this.contactPersonName = contactPersonName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}
