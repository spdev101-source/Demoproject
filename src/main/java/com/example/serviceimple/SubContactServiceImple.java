package com.example.serviceimple;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.dto.request.SubContactRequestDTO;
import com.example.dto.response.SubContactResponseDTO;
import com.example.entity.SubContact;
import com.example.repository.SubContactRepository;
import com.example.service.SubContactService;

@Service
public class SubContactServiceImple implements SubContactService {

	private final SubContactRepository subContactRepository;
	private final ModelMapper modelMapper;

	public SubContactServiceImple(SubContactRepository subContactRepository, ModelMapper modelMapper) {
		this.subContactRepository = subContactRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Optional<SubContactResponseDTO> getSubContactById(Long subContactId) {
		return subContactRepository.findById(subContactId).map(subContact -> modelMapper.map(subContact, SubContactResponseDTO.class));
	}

	@Override
	public SubContactResponseDTO updateSubContact(Long subContactId, SubContactRequestDTO requestDTO) {
		SubContact subContact = subContactRepository.findById(subContactId)
				.orElseThrow(() -> new RuntimeException("Sub-contact Not found with id:" + subContactId));
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
}