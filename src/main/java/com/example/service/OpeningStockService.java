package com.example.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.dto.request.OpeningStockRequestDTO;
import com.example.dto.response.OpeningStockResponseDTO;

public interface OpeningStockService {
	OpeningStockResponseDTO createOpeningStock(OpeningStockRequestDTO requestDTO);
	Optional<OpeningStockResponseDTO> getOpeningStockById(Long openingStockId);
	OpeningStockResponseDTO updateOpeningStock(Long openingStockId, OpeningStockRequestDTO requestDTO);
	boolean deleteOpeningStock(Long openingStockId);
	Page<OpeningStockResponseDTO> getAllOpeningStocks(int page, int size, String sortBy, String direction);
	Page<OpeningStockResponseDTO> searchOpeningStocks(String search, int page, int size, String sortBy, String direction);
}