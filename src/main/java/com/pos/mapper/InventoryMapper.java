package com.pos.mapper;

import com.pos.model.Branch;
import com.pos.model.Inventory;
import com.pos.model.Product;
import com.pos.payload.dto.InventoryDTO;

public class InventoryMapper {

    public static InventoryDTO toDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .quantity(inventory.getQuantity())
                .branchId(
                        inventory.getBranch() != null ? inventory.getBranch().getId() : null
                )
                .productId(
                        inventory.getProduct() != null ? inventory.getProduct().getId() : null
                )
                .product(
                        inventory.getProduct() != null ? ProductMapper.toDTO(inventory.getProduct()) : null
                )
                .lastUpdated(inventory.getLastUpdated())
                .build();
    }


    public static Inventory toEntity(InventoryDTO dto,
                                     Branch branch,
                                     Product product){
        return Inventory.builder()
                .branch(branch)
                .product(product)
                .quantity(dto.getQuantity())
                .build();
    }
}
