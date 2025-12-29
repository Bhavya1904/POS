package com.pos.service.impl;

import com.pos.domain.UserRole;
import com.pos.mapper.UserMapper;
import com.pos.model.Branch;
import com.pos.model.Store;
import com.pos.model.User;
import com.pos.payload.dto.UserDTO;
import com.pos.repository.BranchRepository;
import com.pos.repository.StoreRepository;
import com.pos.repository.UserRepository;
import com.pos.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createStoreEmployee(UserDTO employee, Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new Exception("Store not found")
        );
        Branch branch = null;

        if (employee.getRole() == UserRole.ROLE_BRANCH_MANAGER) {
            if (employee.getBranchId() == null) {
                throw new Exception("Branch id is required to create branch manager");
            }
            branch = branchRepository.findById(employee.getBranchId())
                    .orElseThrow(() -> new Exception("Branch not found"));
        }


        User user = UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));

        User savedEmployee = userRepository.save(user);
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER && branch!=null) {
            branch.setManager(savedEmployee);
            branchRepository.save(branch);
        }
        return UserMapper.toDTO(savedEmployee);
    }

    @Override
    public UserDTO createBranchEmployee(UserDTO employee, Long branchId) throws Exception {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                () -> new Exception("Branch not found")
        );
        if (employee.getRole() == UserRole.ROLE_BRANCH_CASHIER
                                ||
                employee.getRole() == UserRole.ROLE_BRANCH_MANAGER) {
            User user = UserMapper.toEntity(employee);
            user.setBranch(branch);
            user.setStore(branch.getStore());
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            return UserMapper.toDTO(userRepository.save(user));
        }
        throw new Exception("Invalid role");
    }

    @Override
    public UserDTO updateEmployee(Long employeeId, UserDTO employeeDetails) throws Exception {
        User existingEmployee = userRepository.findById(employeeId).orElseThrow(
                () -> new RuntimeException("Employee not found")
        );
        Branch branch = branchRepository.findById(employeeDetails.getBranchId()).orElseThrow(
                () -> new Exception("Branch not found")
        );

        if (employeeDetails.getEmail() != null)
            existingEmployee.setEmail(employeeDetails.getEmail());

        if (employeeDetails.getFullName() != null)
            existingEmployee.setFullName(employeeDetails.getFullName());

        if (employeeDetails.getPhone() != null)
            existingEmployee.setPhone(employeeDetails.getPhone());

        if (employeeDetails.getRole() != null)
            existingEmployee.setRole(employeeDetails.getRole());

        existingEmployee.setPassword(employeeDetails.getPassword());
        existingEmployee.setBranch(branch);
        return UserMapper.toDTO(userRepository.save(existingEmployee));
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        User existingEmployee = userRepository.findById(employeeId).orElseThrow(
                () -> new Exception("Employee not found")
        );
        userRepository.delete(existingEmployee);
    }

    @Override
    public List<UserDTO> findAllEmployeesByStoreId(Long storeId, UserRole role) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new Exception("Store not found")
        );
        return userRepository.findByStore(store)
                .stream().filter(
                        user -> role == null || user.getRole() == role
                ).map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllEmployeesByBranchId(Long branchId, UserRole role) throws Exception {
        Branch branch = branchRepository.findById(branchId).orElseThrow(
                () -> new Exception("Branch not found")
        );
        return userRepository.findByBranchId(branchId)
                .stream().filter(
                        user -> role == null || user.getRole() == role
                ).map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}
