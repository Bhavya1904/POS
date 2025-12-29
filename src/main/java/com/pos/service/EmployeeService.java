package com.pos.service;

import com.pos.domain.UserRole;
import com.pos.model.User;
import com.pos.payload.dto.UserDTO;

import java.util.List;

public interface EmployeeService {

    UserDTO createStoreEmployee(UserDTO employee, Long storeId) throws Exception;
    UserDTO createBranchEmployee(UserDTO employee, Long branchId) throws Exception;
    User updateEmployee(Long employeeId, UserDTO employeeDetails) throws Exception;
    void deleteEmployee(Long employeeId) throws Exception;
    List<User> findAllEmployeesByStoreId(Long storeId, UserRole role) throws Exception;
    List<User> findAllEmployeesByBranchId(Long branchId, UserRole role) throws Exception;

}
