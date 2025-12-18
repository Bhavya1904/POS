package com.pos.service;

import com.pos.exception.UserException;
import com.pos.payload.dto.BranchDTO;

import java.util.List;

public interface BranchService {

    BranchDTO createBranch(BranchDTO dto) throws UserException;
    BranchDTO updateBranch(Long id, BranchDTO dto) throws Exception;
    void deleteBranch(Long id) throws Exception;
    List<BranchDTO> getAllBranchesByStoreId(Long storeId);
    BranchDTO getBranchById(Long id) throws Exception;
}
