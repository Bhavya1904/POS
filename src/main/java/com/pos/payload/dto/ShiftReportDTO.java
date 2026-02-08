package com.pos.payload.dto;


import com.pos.model.PaymentSummary;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShiftReportDTO {
    private Long id;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private Double totalSales;
    private Double totalRefunds;
    private Double netSales;
    private Integer totalOrders;

    private UserDTO cashier;
    private Long cashierId;
    private Long branchId;
    private List<OrderDTO> recentOrders;
    private List<ProductDTO> topSellingProducts;
    private List<RefundDTO> refunds;
    private List<PaymentSummary> paymentSummaries;
}

