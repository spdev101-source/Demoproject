package com.example.controller;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.OpeningStockRequestDTO;
import com.example.dto.response.OpeningStockResponseDTO;
import com.example.dto.response.StockReportResponseDTO;
import com.example.service.OpeningStockService;

@RestController
@RequestMapping("/api/opening-stocks")
public class OpeningStockController {

	private final OpeningStockService openingStockService;

	public OpeningStockController(OpeningStockService openingStockService) {
		this.openingStockService = openingStockService;
	}

	@PostMapping("/save")
	public ResponseEntity<OpeningStockResponseDTO> createOpeningStock(@RequestBody OpeningStockRequestDTO requestDTO) {
		OpeningStockResponseDTO saved = openingStockService.createOpeningStock(requestDTO);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{openingStockId}")
	public ResponseEntity<OpeningStockResponseDTO> getOpeningStockById(@PathVariable Long openingStockId) {
		OpeningStockResponseDTO openingStock = openingStockService.getOpeningStockById(openingStockId)
				.orElseThrow(() -> new RuntimeException("Opening stock Not found with id:" + openingStockId));
		return new ResponseEntity<>(openingStock, HttpStatus.OK);
	}

	@PutMapping("/update/{openingStockId}")
	public OpeningStockResponseDTO updateOpeningStock(@PathVariable Long openingStockId, @RequestBody OpeningStockRequestDTO requestDTO) {
		return openingStockService.updateOpeningStock(openingStockId, requestDTO);
	}

	@DeleteMapping("/{openingStockId}")
	public ResponseEntity<String> deleteOpeningStock(@PathVariable Long openingStockId) {
		boolean deleted = openingStockService.deleteOpeningStock(openingStockId);
		if (!deleted) {
			return new ResponseEntity<>("Opening stock not found with id:" + openingStockId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Opening stock deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public Page<OpeningStockResponseDTO> getAllOpeningStocks(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "openingStockId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return openingStockService.getAllOpeningStocks(page, size, sortBy, direction);
	}

	@GetMapping("/searching")
	public Page<OpeningStockResponseDTO> searchOpeningStocks(
			@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "openingStockId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return openingStockService.searchOpeningStocks(search, page, size, sortBy, direction);
	}
	@GetMapping("/closing-stock")
	public OpeningStockResponseDTO getClosingStock(
	        @RequestParam Long productId,
	        @RequestParam Long warehouseId,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
	    return openingStockService.getClosingStock(productId, warehouseId, fromDate, toDate);
	}
//	@GetMapping("/stock-report")
//	public StockReportResponseDTO getStockReport(
//	        @RequestParam Long productId,
//	        @RequestParam Long warehouseId,
//	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
//	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
//	    return openingStockService.getStockReport(productId, warehouseId, fromDate, toDate);
//	}
	@GetMapping("/closingstock")
    public ResponseEntity<OpeningStockResponseDTO> getClosingStock(
            @RequestParam Long productId,
            @RequestParam Long warehouseId,
            @RequestParam LocalDate toDate) {

        OpeningStockResponseDTO response = openingStockService.getClosingStock(productId, warehouseId, toDate);
        return ResponseEntity.ok(response);
    }
	@GetMapping("/stock-report")
    public ResponseEntity<StockReportResponseDTO> getStockReport(
            @RequestParam Long productId,
            @RequestParam Long warehouseId,
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) {

        StockReportResponseDTO response = openingStockService.getStockReport(productId, warehouseId, fromDate, toDate);
        return ResponseEntity.ok(response);
    }
}