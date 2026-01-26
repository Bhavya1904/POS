package com.pos.payload.dto;

import com.pos.domain.PaymentType;
import com.pos.model.Customer;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;

    private Double totalAmount;

    private LocalDateTime createdAt;

    private Long branchId;
    private BranchDTO branch;

    private UserDTO cashier;

    private Long customerId;
    private Customer customer;

    private PaymentType paymentType;

    private List<OrderItemDTO> items;
}
