package com.pos.mapper;

import com.pos.model.OrderItem;
import com.pos.payload.dto.OrderItemDTO;

public class OrderItemMapper {

    public static OrderItemDTO toDTO(OrderItem orderItem) {
        if (orderItem == null) return null;

        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .productId(
                        orderItem.getProduct() != null
                                ? orderItem.getProduct().getId()
                                : null
                )
                .product(
                        orderItem.getProduct() != null
                                ? ProductMapper.toDTO(orderItem.getProduct())
                                : null
                )

                .build();
    }
}
