package com.foodapp.order.service;

import com.foodapp.order.entity.Order;
import java.util.List;

public interface OrderService {
    Order placeOrder(com.foodapp.order.dto.OrderRequestDTO request);
    List<Order> getOrdersByUser(String userId);

    // ✅ New method for vendors
    List<Order> getOrdersByVendor(String vendorId);
}
