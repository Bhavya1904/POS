package com.pos.service.impl;

import com.pos.exception.UserException;
import com.pos.mapper.BranchMapper;
import com.pos.model.Branch;
import com.pos.model.Store;
import com.pos.model.User;
import com.pos.payload.dto.BranchDTO;
import com.pos.repository.BranchRepository;
import com.pos.repository.StoreRepository;
import com.pos.service.BranchService;
import com.pos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public BranchDTO createBranch(BranchDTO dto) throws UserException {
        User currentUser = userService.getCurrentUser();
        Store store = storeRepository.findByStoreAdminId(currentUser.getId());

        Branch branch = BranchMapper.toEntity(dto, store);
        Branch savedBranch = branchRepository.save(branch);
        return BranchMapper.toDTO(savedBranch);
    }

    @Override
    public BranchDTO updateBranch(Long id, BranchDTO dto) throws Exception {
        Branch existingBranch = branchRepository.findById(id).orElseThrow(
                () -> new Exception("Branch does not exist")
        );
        existingBranch.setName(dto.getName());
        existingBranch.setEmail(dto.getEmail());
        existingBranch.setPhone(dto.getPhone());
        existingBranch.setAddress(dto.getAddress());
        existingBranch.setWorkingDays(dto.getWorkingDays());
        existingBranch.setOpenTime(dto.getOpenTime());
        existingBranch.setCloseTime(dto.getCloseTime());
        existingBranch.setUpdatedAt(LocalDateTime.now());

        Branch updatedBranch = branchRepository.save(existingBranch);

        return BranchMapper.toDTO(updatedBranch);
    }

    @Override
    public void deleteBranch(Long id) throws Exception {
        Branch existingBranch = branchRepository.findById(id).orElseThrow(
                () -> new Exception("Branch does not exist")
        );
        branchRepository.delete(existingBranch);
    }

    @Override
    public List<BranchDTO> getAllBranchesByStoreId(Long storeId) {
        List<Branch> branches = branchRepository.findByStoreId(storeId);
        return branches.stream().map(BranchMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BranchDTO getBranchById(Long id) throws Exception {
        Branch existingBranch = branchRepository.findById(id).orElseThrow(
                () -> new Exception("Branch does not exist")
        );
        return BranchMapper.toDTO(existingBranch);
    }
}
