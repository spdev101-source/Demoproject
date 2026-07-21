package com.example.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.request.CustomerRequestDTO;
import com.example.dto.response.CustomerResponseDTO;
import com.example.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping("/save")
	public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO requestDTO) {
		CustomerResponseDTO saved = customerService.createCustomer(requestDTO);
		return new ResponseEntity<CustomerResponseDTO>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/get/{customerId}")
	public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long customerId) {
		CustomerResponseDTO customer = customerService.getCustomerById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found with id:" + customerId));
		return new ResponseEntity<CustomerResponseDTO>(customer,HttpStatus.OK);
	}
	@PutMapping("/update/{customerId}")
	public CustomerResponseDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerRequestDTO requestDTO)
	{
		return customerService.updateCustomer(customerId, requestDTO);
	}
	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId)
	{
		boolean deleted=customerService.deleteCustomer(customerId);
		if(!deleted)
		{
			return new ResponseEntity<String>("Customer not found with id:"+customerId,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Customer deleted Successfully",HttpStatus.OK);

	}
	@GetMapping("/getAll")
	public Page<CustomerResponseDTO> getAllCustomers(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction)
	{
		return customerService.getAllCustomers(page, size, sortBy, direction);
	}
	 @GetMapping("/searching")
	    public Page<CustomerResponseDTO> searchCustomer(
	            @RequestParam String search,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "5") int size,
	            @RequestParam(defaultValue = "customerId") String sortBy,
	            @RequestParam(defaultValue = "asc") String direction) {
	        return customerService.searchCustomer(search, page, size, sortBy, direction);
	    }
//	 @PostMapping("/add-with-contacts")
//	 public ResponseEntity<CustomerResponseDTO> addCustomerWithContacts(@RequestBody CustomerRequestDTO request) {
//	     CustomerResponseDTO saved = customerService.addCustomerWithContacts(request);
//	     return new ResponseEntity<>(saved, HttpStatus.CREATED);
//	 }
	 /*
	  {
  "customerName": "Acme1 Corp",
  "customerPhone": "9876543010",
  "customerEmail": "acme1@example.com",
  "city": "Mumbai",
  "country": "India",
  "state": "Maharashtra",
  "subContacts": [
    { "contactPersonName": "Raj1 Kumar", "contactPhone": "9111111111", "contactEmail": "raj@acme.com" },
    { "contactPersonName": "Priya2 Sharma", "contactPhone": "9222222222", "contactEmail": "priya@acme.com" }
  ]
}
	  */
}
