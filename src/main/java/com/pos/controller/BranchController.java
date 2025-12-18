package com.pos.controller;

import com.pos.exception.UserException;
import com.pos.payload.dto.BranchDTO;
import com.pos.payload.response.ApiResponse;
import com.pos.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(
            @RequestBody BranchDTO branchDto) throws UserException {
        BranchDTO createdBranch = branchService.createBranch(branchDto);

        return ResponseEntity.ok(createdBranch);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(
            @PathVariable Long id) throws Exception {
        BranchDTO createdBranch = branchService.getBranchById(id);

        return ResponseEntity.ok(createdBranch);
    }
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDTO>> getAllBranchesByStoreId(
            @PathVariable Long storeId) {
        List<BranchDTO> createdBranch = branchService.getAllBranchesByStoreId(storeId);

        return ResponseEntity.ok(createdBranch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(
            @PathVariable Long id,
            @RequestBody BranchDTO dto) throws Exception {
        BranchDTO createdBranch = branchService.updateBranch(id, dto);

        return ResponseEntity.ok(createdBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBranch(
            @PathVariable Long id) throws Exception {

        branchService.deleteBranch(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Branch deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }


}


