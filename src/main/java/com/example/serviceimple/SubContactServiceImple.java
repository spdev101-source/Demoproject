package com.example.serviceimple;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.example.dto.request.SubContactRequestDTO;
import com.example.dto.response.SubContactResponseDTO;
import com.example.entity.Customer;
import com.example.entity.SubContact;
import com.example.repository.CustomerRepository;
import com.example.repository.SubContactRepository;
import com.example.service.SubContactService;

@Service
public class SubContactServiceImple implements SubContactService {

	private final SubContactRepository subContactRepository;
	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper;

	public SubContactServiceImple(SubContactRepository subContactRepository, CustomerRepository customerRepository,
			ModelMapper modelMapper) {
		this.subContactRepository = subContactRepository;
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public SubContactResponseDTO createSubContact(SubContactRequestDTO requestDTO) {
		// Step 1: load the customer (the "folder") this contact goes into
		Customer customer = customerRepository.findById(requestDTO.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + requestDTO.getCustomerId()));

		if (subContactRepository.existsByContactEmail(requestDTO.getContactEmail())) {
			throw new RuntimeException("Sub-contact Already exists with Email:" + requestDTO.getContactEmail());
		}

		// Step 2: build the new sub-contact and link it to the customer
		SubContact subContact = new SubContact();
		subContact.setContactPersonName(requestDTO.getContactPersonName());
		subContact.setContactPhone(requestDTO.getContactPhone());
		subContact.setContactEmail(requestDTO.getContactEmail());
		subContact.setCustomer(customer);

		// Step 3: save the SUBCONTACT directly — this triggers the insert immediately
		// and returns the managed entity with its generated ID populated
		SubContact saved = subContactRepository.save(subContact);

		// Step 4: keep the customer's in-memory list in sync (optional but good practice)
		customer.getSubContacts().add(saved);

		SubContactResponseDTO dto = modelMapper.map(saved, SubContactResponseDTO.class);
		dto.setCustomerName(customer.getCustomerName());
		return dto;
	}

	@Override
	public Optional<SubContactResponseDTO> getSubContactById(Long subContactId) {
		return subContactRepository.findById(subContactId)
				.map(subContact -> modelMapper.map(subContact, SubContactResponseDTO.class));
	}

	@Override
	public SubContactResponseDTO updateSubContact(Long subContactId, SubContactRequestDTO requestDTO) {
		SubContact subContact = subContactRepository.findById(subContactId)
				.orElseThrow(() -> new RuntimeException("Sub-contact Not found with id:" + subContactId));

		if (subContactRepository.existsByContactEmailAndSubContactIdNot(requestDTO.getContactEmail(), subContactId)) {
			throw new RuntimeException("Sub-contact Already exists with Email:" + requestDTO.getContactEmail());
		}

		subContact.setContactPersonName(requestDTO.getContactPersonName());
		subContact.setContactPhone(requestDTO.getContactPhone());
		subContact.setContactEmail(requestDTO.getContactEmail());
		SubContact updated = subContactRepository.save(subContact);

		return modelMapper.map(updated, SubContactResponseDTO.class);
	}

	@Override
	public boolean deleteSubContact(Long subContactId) {
		if (subContactRepository.existsById(subContactId)) {
			subContactRepository.deleteById(subContactId);
			return true;
		}
		return false;
	}

	@Override
	public List<SubContactResponseDTO> getSubContactsByCustomerId(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer Not found with id:" + customerId));
		return customer.getSubContacts().stream()
				.map(subContact -> {
					SubContactResponseDTO dto = modelMapper.map(subContact, SubContactResponseDTO.class);
					dto.setCustomerName(customer.getCustomerName());
					return dto;
				})
				.toList();
	}
}