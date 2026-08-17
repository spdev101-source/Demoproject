package com.example.serviceimple;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dto.request.OpeningStockRequestDTO;
import com.example.dto.response.OpeningStockResponseDTO;
import com.example.dto.response.StockReportResponseDTO;
import com.example.entity.OpeningStock;
import com.example.entity.Product;
import com.example.entity.Warehouse;
import com.example.repository.OpeningStockRepository;
import com.example.repository.ProductRepository;
import com.example.repository.WarehouseRepository;
import com.example.service.OpeningStockService;

@Service
public class OpeningStockServiceImple implements OpeningStockService {

	private final OpeningStockRepository openingStockRepository;
	private final ProductRepository productRepository;
	private final WarehouseRepository warehouseRepository;
	private final ModelMapper modelMapper;

	public OpeningStockServiceImple(OpeningStockRepository openingStockRepository, ProductRepository productRepository,
			WarehouseRepository warehouseRepository, ModelMapper modelMapper) {
		this.openingStockRepository = openingStockRepository;
		this.productRepository = productRepository;
		this.warehouseRepository = warehouseRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public OpeningStockResponseDTO createOpeningStock(OpeningStockRequestDTO requestDTO) {
		Product product = productRepository.findById(requestDTO.getProductId())
				.orElseThrow(() -> new RuntimeException("Product Not found with id:" + requestDTO.getProductId()));
		Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
				.orElseThrow(() -> new RuntimeException("Warehouse Not found with id:" + requestDTO.getWarehouseId()));
//		// without the helper — repeated in both create and update
//		if (requestDTO.getQuantity() == null || requestDTO.getQuantity() < 0) {
//			throw new RuntimeException("Quantity cannot be negative");
//		}
//		if (requestDTO.getOpeningDate() == null || requestDTO.getOpeningDate().isAfter(LocalDate.now())) {
//			throw new RuntimeException("Opening date cannot be in the future");
//		}
		if (openingStockRepository.existsByProductProductIdAndWarehouseWarehouseIdAndOpeningDate(
		        requestDTO.getProductId(), requestDTO.getWarehouseId(), requestDTO.getOpeningDate())) {
		    throw new RuntimeException("Opening stock already exists for this product/warehouse on this date");
		}

		OpeningStock openingStock = new OpeningStock();
		openingStock.setProduct(product);
		openingStock.setWarehouse(warehouse);
		openingStock.setQuantity(requestDTO.getQuantity());
		openingStock.setOpeningDate(requestDTO.getOpeningDate());

		OpeningStock saved = openingStockRepository.save(openingStock);
		return toResponseDTO(saved);
	}

	@Override
	public Optional<OpeningStockResponseDTO> getOpeningStockById(Long openingStockId) {
		return openingStockRepository.findById(openingStockId).map(this::toResponseDTO);
	}

	@Override
	public OpeningStockResponseDTO updateOpeningStock(Long openingStockId, OpeningStockRequestDTO requestDTO) {
		OpeningStock openingStock = openingStockRepository.findById(openingStockId)
				.orElseThrow(() -> new RuntimeException("Opening stock Not found with id:" + openingStockId));

		Product product = productRepository.findById(requestDTO.getProductId())
				.orElseThrow(() -> new RuntimeException("Product Not found with id:" + requestDTO.getProductId()));
		Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
				.orElseThrow(() -> new RuntimeException("Warehouse Not found with id:" + requestDTO.getWarehouseId()));

		if (openingStockRepository.existsByProductProductIdAndWarehouseWarehouseIdAndOpeningDateAndOpeningStockIdNot(
		        requestDTO.getProductId(), requestDTO.getWarehouseId(), requestDTO.getOpeningDate(), openingStockId)) {
		    throw new RuntimeException("Opening stock already exists for this product/warehouse on this date");
		}
		openingStock.setProduct(product);
		openingStock.setWarehouse(warehouse);
		openingStock.setQuantity(requestDTO.getQuantity());
		openingStock.setOpeningDate(requestDTO.getOpeningDate());

		OpeningStock updated = openingStockRepository.save(openingStock);
		return toResponseDTO(updated);
	}

	@Override
	public boolean deleteOpeningStock(Long openingStockId) {
		if (openingStockRepository.existsById(openingStockId)) {
			openingStockRepository.deleteById(openingStockId);
			return true;
		}
		return false;
	}

	@Override
	public Page<OpeningStockResponseDTO> getAllOpeningStocks(int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return openingStockRepository.findAll(pageable).map(this::toResponseDTO);
	}

	@Override
	public Page<OpeningStockResponseDTO> searchOpeningStocks(String search, int page, int size, String sortBy, String direction) {
		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);
		return openingStockRepository.searchOpeningStocks(search, pageable).map(this::toResponseDTO);
	}

	private OpeningStockResponseDTO toResponseDTO(OpeningStock openingStock) {
		OpeningStockResponseDTO dto = modelMapper.map(openingStock, OpeningStockResponseDTO.class);
		dto.setProductName(openingStock.getProduct().getProductName());
		dto.setWarehouseName(openingStock.getWarehouse().getWarehouseName());
		return dto;
	}

	@Override
	public OpeningStockResponseDTO getClosingStock(Long productId, Long warehouseId, LocalDate fromDate, LocalDate toDate) {

	    if (fromDate == null || toDate == null) {
	        throw new RuntimeException("fromDate and toDate are required");
	    }
	    if (fromDate.isAfter(toDate)) {
	        throw new RuntimeException("fromDate cannot be after toDate");
	    }

	    List<OpeningStock> candidates = openingStockRepository
	            .findClosingStockCandidates(productId, warehouseId, toDate);

	    if (candidates.isEmpty()) {
	        throw new RuntimeException(
	                "No opening stock record found on or before " + toDate + " for this product and warehouse");
	    }

	    OpeningStock latestRecord = candidates.get(0);

	    return toResponseDTO(latestRecord);
	}
//	@Override
//	public StockReportResponseDTO getStockReport(Long productId, Long warehouseId, LocalDate fromDate, LocalDate toDate) {
//
//	    if (fromDate == null || toDate == null) {
//	        throw new RuntimeException("fromDate and toDate are required");
//	    }
//	    if (fromDate.isAfter(toDate)) {
//	        throw new RuntimeException("fromDate cannot be after toDate");
//	    }
//
//	    return openingStockRepository.getStockReport(productId, warehouseId, fromDate, toDate);
//	}

	

	    @Override
	    public OpeningStockResponseDTO getClosingStock(Long productId, Long warehouseId, LocalDate toDate) {

	        Integer result = openingStockRepository.getClosingStock(productId, warehouseId, toDate);

	        int closingStock;
	        if (result != null) {
	            closingStock = result;
	        } else {
	            closingStock = 0;
	        }

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found"));
	        Warehouse warehouse = warehouseRepository.findById(warehouseId)
	                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

	        OpeningStockResponseDTO response = new OpeningStockResponseDTO();
	        response.setProductName(product.getProductName());
	        response.setWarehouseName(warehouse.getWarehouseName());
	        response.setQuantity(closingStock);
	        response.setOpeningDate(toDate);

	        return response;
	    }

	    

	        @Override
	        public StockReportResponseDTO getStockReport(Long productId, Long warehouseId,
	                                                       LocalDate fromDate, LocalDate toDate) {

	            Integer openingResult = openingStockRepository.getOpeningQuantity(productId, warehouseId, fromDate);
	            int openingQuantity;
	            if (openingResult != null) {
	                openingQuantity = openingResult;
	            } else {
	                openingQuantity = 0;
	            }

	            Integer periodResult = openingStockRepository.getPeriodQuantity(productId, warehouseId, fromDate, toDate);
	            int periodQuantity;
	            if (periodResult != null) {
	                periodQuantity = periodResult;
	            } else {
	                periodQuantity = 0;
	            }

	            int closingQuantity = openingQuantity + periodQuantity;

	            Product product = productRepository.findById(productId)
	                    .orElseThrow(() -> new RuntimeException("Product not found"));
	            Warehouse warehouse = warehouseRepository.findById(warehouseId)
	                    .orElseThrow(() -> new RuntimeException("Warehouse not found"));

	            StockReportResponseDTO response = new StockReportResponseDTO();
	            response.setProductId(productId);
	            response.setProductName(product.getProductName());
	            response.setWarehouseId(warehouseId);
	            response.setWarehouseName(warehouse.getWarehouseName());
	            response.setFromDate(fromDate);
	            response.setToDate(toDate);
	            response.setOpeningQuantity(openingQuantity);
	            response.setPeriodQuantity(periodQuantity);
	            response.setClosingQuantity(closingQuantity);

	            return response;
	        }
	    }
	

//	    @Override
//	    public OpeningStockResponseDTO getClosingStock(OpeningStockRequestDTO requestDTO) {
//
////	        Integer result = openingStockRepository.getClosingStock(
////	                requestDTO.getProductId(),
////	                requestDTO.getWarehouseId(),
////	                requestDTO.getOpeningDate());
//
//	        int closingStock;
//	        if (result != null) {
//	            closingStock = result;
//	        } else {
//	            closingStock = 0;
//	        }
//
//	        Product product = productRepository.findById(requestDTO.getProductId())
//	                .orElseThrow(() -> new RuntimeException("Product not found"));
//	        Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
//	                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
//
//	        OpeningStockResponseDTO response = new OpeningStockResponseDTO();
//	        response.setProductName(product.getProductName());
//	        response.setWarehouseName(warehouse.getWarehouseName());
//	        response.setQuantity(closingStock);
//	        response.setOpeningDate(requestDTO.getOpeningDate());
//
//	        return response;
//	    }
	

/*
 private void validateQuantity(Integer quantity) {
		if (quantity == null || quantity < 0) {
			throw new RuntimeException("Quantity cannot be negative");
		}
	}

	private void validateDate(LocalDate openingDate) {
		if (openingDate == null || openingDate.isAfter(LocalDate.now())) {
			throw new RuntimeException("Opening date cannot be in the future");
		}
	}
 */
