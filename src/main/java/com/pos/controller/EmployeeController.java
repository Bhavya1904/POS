package com.pos.controller;

import com.pos.domain.UserRole;
import com.pos.model.User;
import com.pos.payload.dto.UserDTO;
import com.pos.payload.response.ApiResponse;
import com.pos.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDTO> createEmployee(
            @RequestBody UserDTO userDTO,
            @PathVariable Long storeId) throws Exception {
        UserDTO employee = employeeService.createStoreEmployee(userDTO, storeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDTO> createBranchEmployee(
            @RequestBody UserDTO userDTO,
            @PathVariable Long branchId) throws Exception {
        UserDTO employee = employeeService.createBranchEmployee(userDTO, branchId);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateEmployee(
            @RequestBody UserDTO userDTO,
            @PathVariable Long id) throws Exception {
        User employee = employeeService.updateEmployee(id, userDTO);
        return ResponseEntity.ok(employee);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> updateEmployee(
            @PathVariable Long id) throws Exception {
        employeeService.deleteEmployee(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Employee deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<User>> storeEmployees(
            @PathVariable Long storeId,
            @RequestParam(required = false)UserRole userRole) throws Exception {
        List<User> employee = employeeService.findAllEmployeesByStoreId(storeId, userRole);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<User>> branchEmployees(
            @PathVariable Long branchId,
            @RequestParam(required = false)UserRole userRole) throws Exception {
        List<User> employee = employeeService.findAllEmployeesByBranchId(branchId, userRole);
        return ResponseEntity.ok(employee);
    }



}



