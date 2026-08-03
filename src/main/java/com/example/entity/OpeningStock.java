package com.example.entity;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "opening_stocks")
public class OpeningStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long openingStockId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_id")
	private Warehouse warehouse;

	private Integer quantity;

	private LocalDate openingDate;

	public Long getOpeningStockId() {
		return openingStockId;
	}
	public void setOpeningStockId(Long openingStockId) {
		this.openingStockId = openingStockId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public LocalDate getOpeningDate() {
		return openingDate;
	}
	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
	}
}
