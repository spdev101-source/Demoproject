package com.example.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dto.request.SubContactRequestDTO;
import com.example.dto.response.SubContactResponseDTO;
import com.example.service.SubContactService;

@RestController
@RequestMapping("/api/subcontacts")
public class SubContactController {

	private final SubContactService subContactService;

	public SubContactController(SubContactService subContactService) {
		this.subContactService = subContactService;
	}

	@PostMapping("/save")
	public ResponseEntity<SubContactResponseDTO> createSubContact(@RequestBody SubContactRequestDTO requestDTO) {
		SubContactResponseDTO saved = subContactService.createSubContact(requestDTO);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{subContactId}")
	public ResponseEntity<SubContactResponseDTO> getSubContactById(@PathVariable Long subContactId) {
		SubContactResponseDTO subContact = subContactService.getSubContactById(subContactId)
				.orElseThrow(() -> new RuntimeException("Sub-contact Not found with id:" + subContactId));
		return new ResponseEntity<>(subContact, HttpStatus.OK);
	}

	@PutMapping("/update/{subContactId}")
	public SubContactResponseDTO updateSubContact(@PathVariable Long subContactId, @RequestBody SubContactRequestDTO requestDTO) {
		return subContactService.updateSubContact(subContactId, requestDTO);
	}

	@DeleteMapping("/{subContactId}")
	public ResponseEntity<String> deleteSubContact(@PathVariable Long subContactId) {
		boolean deleted = subContactService.deleteSubContact(subContactId);
		if (!deleted) {
			return new ResponseEntity<>("Sub-contact not found with id:" + subContactId, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Sub-contact deleted Successfully", HttpStatus.OK);
	}

	@GetMapping("/customer/{customerId}")
	public List<SubContactResponseDTO> getSubContactsByCustomerId(@PathVariable Long customerId) {
		return subContactService.getSubContactsByCustomerId(customerId);
	}
}