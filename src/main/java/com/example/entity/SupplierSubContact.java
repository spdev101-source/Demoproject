package com.example.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "supplier_sub_contacts")
public class SupplierSubContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subContactId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private String contactPersonName;
    private String contactPhone;
    private String contactEmail;

    public SupplierSubContact() {}

    public Long getSubContactId() { return subContactId; }
    public void setSubContactId(Long subContactId) { this.subContactId = subContactId; }

    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }

    public String getContactPersonName() { return contactPersonName; }
    public void setContactPersonName(String contactPersonName) { this.contactPersonName = contactPersonName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
}
