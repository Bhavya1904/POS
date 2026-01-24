package com.pos.service;

import com.pos.domain.OrderStatus;
import com.pos.domain.PaymentType;
import com.pos.payload.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO) throws Exception;
    OrderDTO getOrderById(Long id) throws Exception;
    List<OrderDTO> getOrdersByBranch(Long branchId,
                                     Long customerId,
                                     Long cashierId,
                                     PaymentType paymentType,
                                     OrderStatus status) throws Exception;
    List<OrderDTO> getOrdersByCashierId(Long cashierId) throws Exception ;
    void deleteOrder(Long id) throws Exception;
    List<OrderDTO> getTodayOrdersByBranch(Long branchId) throws Exception;
    List<OrderDTO> getOrdersByCustomerId(Long cashierId) throws Exception;
    List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId) throws Exception;

}


