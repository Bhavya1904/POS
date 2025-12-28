package com.pos.service.impl;

import com.pos.mapper.InventoryMapper;
import com.pos.model.Branch;
import com.pos.model.Inventory;
import com.pos.model.Product;
import com.pos.payload.dto.InventoryDTO;
import com.pos.repository.BranchRepository;
import com.pos.repository.InventoryRepository;
import com.pos.repository.ProductRepository;
import com.pos.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) throws Exception {
        Branch existingBranch = branchRepository.findById(inventoryDTO.getBranchId())
                .orElseThrow(
                    () -> new Exception("Branch does not exist")
        );
        Product product = productRepository.findById(inventoryDTO.getProductId())
                .orElseThrow(
                    () -> new Exception("Product does not exist")
        );

        Inventory inventory = InventoryMapper.toEntity(inventoryDTO, existingBranch, product);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return InventoryMapper.toDTO(savedInventory);
    }

    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) throws Exception {
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Branch does not exist")
                );
        existingInventory.setQuantity(inventoryDTO.getQuantity());

        Inventory updatedInventory = inventoryRepository.save(existingInventory);
        return InventoryMapper.toDTO(updatedInventory);
    }

    @Override
    public void deleteInventory(Long id) throws Exception {
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Branch does not exist")
                );
        inventoryRepository.delete(existingInventory);
    }

    @Override
    public InventoryDTO getInventoryById(Long id) throws Exception {
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("Branch does not exist")
                );
        return InventoryMapper.toDTO(existingInventory);
    }

    @Override
    public InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId) {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);
        return InventoryMapper.toDTO(inventory);
    }

    @Override
    public List<InventoryDTO> getAllInventoryByBranchId(Long branchId) {
        List<Inventory> inventories = inventoryRepository.findByBranchId(branchId);
        return inventories.stream().map(
                InventoryMapper::toDTO
        ).collect(Collectors.toList());
    }
}
