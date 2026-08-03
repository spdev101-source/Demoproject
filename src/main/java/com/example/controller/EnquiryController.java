package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.request.EnquiryRequestDTO;
import com.example.dto.response.EnquiryResponseDTO;
import com.example.enums.EnquiryStatus;
import com.example.service.EnquiryService;

import java.util.List;

@RestController
@RequestMapping("/api/enquiries")
public class EnquiryController {

	private final EnquiryService enquiryService;

	public EnquiryController(EnquiryService enquiryService) {
		this.enquiryService = enquiryService;
	}

	@PostMapping("/save")
	public ResponseEntity<EnquiryResponseDTO> createEnquiry(@RequestBody EnquiryRequestDTO requestDTO) {
		EnquiryResponseDTO saved = enquiryService.createEnquiry(requestDTO);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{enquiryId}")
	public ResponseEntity<EnquiryResponseDTO> getEnquiryById(@PathVariable Long enquiryId) {
		EnquiryResponseDTO enquiry = enquiryService.getEnquiryById(enquiryId)
				.orElseThrow(() -> new RuntimeException("Enquiry Not found with id:" + enquiryId));
		return new ResponseEntity<>(enquiry, HttpStatus.OK);
	}

	@PutMapping("/update/{enquiryId}")
	public EnquiryResponseDTO updateEnquiry(@PathVariable Long enquiryId, @RequestBody EnquiryRequestDTO requestDTO) {
		return enquiryService.updateEnquiry(enquiryId, requestDTO);
	}

	@DeleteMapping("/{enquiryId}")
	public ResponseEntity<String> deleteEnquiry(@PathVariable Long enquiryId) {
		boolean deleted = enquiryService.deleteEnquiry(enquiryId);
		if (!deleted) {
			return new ResponseEntity<>("Enquiry not found with id:" + enquiryId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Enquiry deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public Page<EnquiryResponseDTO> getAllEnquiries(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "enquiryId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return enquiryService.getAllEnquiries(page, size, sortBy, direction);
	}

	@GetMapping("/searching")
	public Page<EnquiryResponseDTO> searchEnquiries(
			@RequestParam String search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "enquiryId") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return enquiryService.searchEnquiries(search, page, size, sortBy, direction);
	}

	@GetMapping("/customer/{customerId}")
	public List<EnquiryResponseDTO> getEnquiriesByCustomerId(@PathVariable Long customerId) {
		return enquiryService.getEnquiriesByCustomerId(customerId);
	}

	@GetMapping("/status/{status}")
	public List<EnquiryResponseDTO> getEnquiriesByStatus(@PathVariable EnquiryStatus status) {
		return enquiryService.getEnquiriesByStatus(status);
	}

	@PutMapping("/close/{enquiryId}")
	public EnquiryResponseDTO closeEnquiry(@PathVariable Long enquiryId) {
		return enquiryService.closeEnquiry(enquiryId);
	}
}