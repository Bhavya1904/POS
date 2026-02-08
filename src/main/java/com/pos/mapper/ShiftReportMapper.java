package com.pos.mapper;


import com.pos.model.Order;
import com.pos.model.Product;
import com.pos.model.Refund;
import com.pos.model.ShiftReport;
import com.pos.payload.dto.OrderDTO;
import com.pos.payload.dto.ProductDTO;
import com.pos.payload.dto.RefundDTO;
import com.pos.payload.dto.ShiftReportDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShiftReportMapper {

    public static ShiftReportDTO toDTO(ShiftReport shiftReport) {
        if (shiftReport == null) return null;

        return ShiftReportDTO.builder()
                .id(shiftReport.getId())
                .shiftStart(shiftReport.getShiftStart())
                .shiftEnd(shiftReport.getShiftEnd())
                .totalSales(shiftReport.getTotalSales() != null ? shiftReport.getTotalSales() : 0.0)
                .totalRefunds(shiftReport.getTotalRefunds() != null ? shiftReport.getTotalRefunds() : 0.0)
                .netSales(shiftReport.getNetSales() != null ? shiftReport.getNetSales() : 0.0)
                .totalOrders(shiftReport.getTotalOrders())
                .cashier(shiftReport.getCashier() != null
                        ? UserMapper.toDTO(shiftReport.getCashier())
                        : null)
                .cashierId(shiftReport.getCashier() != null
                        ? shiftReport.getCashier().getId()
                        : null)
                .branchId(shiftReport.getBranch() != null
                        ? shiftReport.getBranch().getId()
                        : null)
                .recentOrders(mapOrders(shiftReport.getRecentOrders()))
                .topSellingProducts(mapProducts(shiftReport.getTopSellingProducts()))
                .refunds(mapRefunds(shiftReport.getRefunds()))
                .paymentSummaries(shiftReport.getPaymentSummaries())
                .build();

    }

    private static List<OrderDTO> mapOrders(List<Order> orders) {
        if (orders == null) return List.of();
        return orders.stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static List<ProductDTO> mapProducts(List<Product> products) {
        if (products == null) return List.of();
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static List<RefundDTO> mapRefunds(List<Refund> refunds) {
        if (refunds == null) return List.of();
        return refunds.stream()
                .map(RefundMapper::toDTO)
                .collect(Collectors.toList());
    }
}
