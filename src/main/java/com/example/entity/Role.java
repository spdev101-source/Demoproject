package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*
	 @Query("SELECT o.warehouse.warehouseId AS warehouseId, " +
       "o.warehouse.warehouseName AS warehouseName, " +
       "SUM(o.quantity) AS closingStock " +
       "FROM OpeningStock o " +
       "WHERE o.product.productId = :productId " +
       "AND o.openingDate <= :toDate " +
       "GROUP BY o.warehouse.warehouseId, o.warehouse.warehouseName")
List<WarehouseStockProjection> getWarehouseWiseStock(@Param("productId") Long productId,
                                                        @Param("toDate") LocalDate toDate);
                                                        
   public interface WarehouseStockProjection {
    Long getWarehouseId();
    String getWarehouseName();
    Integer getClosingStock();
}
public class WarehouseStockResponseDTO {

	private Long warehouseId;
	private String warehouseName;
	private Integer closingStock;

	public Long getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public Integer getClosingStock() {
		return closingStock;
	}
	public void setClosingStock(Integer closingStock) {
		this.closingStock = closingStock;
	}
}public interface WarehouseStockService {

    List<WarehouseStockResponseDTO> getWarehouseWiseStock(Long productId, LocalDate toDate);
}
@Service
public class WarehouseStockServiceImpl implements WarehouseStockService {

    @Autowired
    private OpeningStockRepository openingStockRepository;

    @Override
    public List<WarehouseStockResponseDTO> getWarehouseWiseStock(Long productId, LocalDate toDate) {

        List<WarehouseStockProjection> results = openingStockRepository.getWarehouseWiseStock(productId, toDate);

        List<WarehouseStockResponseDTO> response = new ArrayList<>();

        for (WarehouseStockProjection item : results) {
            WarehouseStockResponseDTO dto = new WarehouseStockResponseDTO();
            dto.setWarehouseId(item.getWarehouseId());
            dto.setWarehouseName(item.getWarehouseName());
            dto.setClosingStock(item.getClosingStock());
            response.add(dto);
        }

        return response;
    }
}
@RestController
@RequestMapping("/api/stock")
public class WarehouseStockController {

    @Autowired
    private WarehouseStockService warehouseStockService;

    @GetMapping("/warehouse-wise")
    public ResponseEntity<List<WarehouseStockResponseDTO>> getWarehouseWiseStock(
            @RequestParam Long productId,
            @RequestParam LocalDate toDate) {

        List<WarehouseStockResponseDTO> response = warehouseStockService.getWarehouseWiseStock(productId, toDate);
        return ResponseEntity.ok(response);
    }
}
GET /api/stock/warehouse-wise?productId=1&toDate=2026-08-10
@Query("SELECT o.product.productId AS productId, " +
       "o.product.productName AS productName, " +
       "SUM(o.quantity) AS closingStock " +
       "FROM OpeningStock o " +
       "WHERE o.warehouse.warehouseId = :warehouseId " +
       "AND o.openingDate <= :toDate " +
       "GROUP BY o.product.productId, o.product.productName")
List<ProductStockProjection> getProductWiseStock(@Param("warehouseId") Long warehouseId,
                                                    @Param("toDate") LocalDate toDate);
                            public interface ProductStockProjection {
    Long getProductId();
    String getProductName();
    Integer getClosingStock();
}
public class ProductStockResponseDTO {

	private Long productId;
	private String productName;
	private Integer closingStock;

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getClosingStock() {
		return closingStock;
	}
	public void setClosingStock(Integer closingStock) {
		this.closingStock = closingStock;
	}
}
public interface ProductStockService {

    List<ProductStockResponseDTO> getProductWiseStock(Long warehouseId, LocalDate toDate);
}
@Service
public class ProductStockServiceImpl implements ProductStockService {

    @Autowired
    private OpeningStockRepository openingStockRepository;

    @Override
    public List<ProductStockResponseDTO> getProductWiseStock(Long warehouseId, LocalDate toDate) {

        List<ProductStockProjection> results = openingStockRepository.getProductWiseStock(warehouseId, toDate);

        List<ProductStockResponseDTO> response = new ArrayList<>();

        for (ProductStockProjection item : results) {
            ProductStockResponseDTO dto = new ProductStockResponseDTO();
            dto.setProductId(item.getProductId());
            dto.setProductName(item.getProductName());
            dto.setClosingStock(item.getClosingStock());
            response.add(dto);
        }

        return response;
    }
}
@RestController
@RequestMapping("/api/stock")
public class ProductStockController {

    @Autowired
    private ProductStockService productStockService;

    @GetMapping("/product-wise")
    public ResponseEntity<List<ProductStockResponseDTO>> getProductWiseStock(
            @RequestParam Long warehouseId,
            @RequestParam LocalDate toDate) {

        List<ProductStockResponseDTO> response = productStockService.getProductWiseStock(warehouseId, toDate);
        return ResponseEntity.ok(response);
    }
}
GET /api/stock/product-wise?warehouseId=1&toDate=2026-08-10
	 */
}
