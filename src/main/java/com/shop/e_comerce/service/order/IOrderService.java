package com.shop.e_comerce.service.order;

import com.shop.e_comerce.DTO.OrderDto;
import com.shop.e_comerce.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
