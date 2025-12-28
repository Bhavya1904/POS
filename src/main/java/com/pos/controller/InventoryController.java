package com.pos.controller;

import com.pos.payload.dto.InventoryDTO;
import com.pos.payload.response.ApiResponse;
import com.pos.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventories")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping()
    public ResponseEntity<InventoryDTO> createInventory(
            @RequestBody InventoryDTO inventoryDTO) throws Exception {
        return ResponseEntity.ok(inventoryService.createInventory(inventoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(
            @RequestBody InventoryDTO inventoryDTO,
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInventory(
            @PathVariable Long id) throws Exception {
        inventoryService.deleteInventory(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Inventory deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryDTO> getInventoryById(
            @PathVariable Long inventoryId) throws Exception {
        return ResponseEntity.ok(inventoryService.getInventoryById(inventoryId));
    }

    @GetMapping("/branch/{branchId}/product/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProductAndBranchId(
            @PathVariable Long branchId,
            @PathVariable Long productId){
        return ResponseEntity.ok(
                inventoryService.getInventoryByProductIdAndBranchId(productId,branchId)
        );
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<InventoryDTO>> getInventoryByBranch(
            @PathVariable Long branchId){
        return ResponseEntity.ok(inventoryService.getAllInventoryByBranchId(branchId));
    }


}
