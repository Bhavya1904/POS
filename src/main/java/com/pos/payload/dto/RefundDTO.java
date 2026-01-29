package com.pos.payload.dto;

import com.pos.domain.PaymentType;
import com.pos.model.ShiftReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {
    private Long id;

    private OrderDTO orderDTO;
    private Long orderId;

    private String reason;

    private Double amount;

    private ShiftReport shiftReport;
    private Long shiftReportId;

    private UserDTO cashier;
    private String cashierName;

    private BranchDTO branch;
    private Long branchId;

    private PaymentType paymentType;

    private LocalDateTime createdAt;
}
