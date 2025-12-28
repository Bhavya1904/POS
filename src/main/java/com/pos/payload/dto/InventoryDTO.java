package com.pos.payload.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    private Long id;

    private BranchDTO branch;
    private Long branchId;

    private ProductDTO product;
    private Long productId;

    private Integer quantity;

    private LocalDateTime lastUpdated;

}



