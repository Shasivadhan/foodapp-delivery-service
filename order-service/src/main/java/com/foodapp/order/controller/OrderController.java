package com.foodapp.order.controller;

import com.foodapp.order.dto.OrderRequestDTO;
import com.foodapp.order.entity.Order;
import com.foodapp.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place a new order
    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDTO dto) {
        try {
            Order order = orderService.placeOrder(dto);
            return ResponseEntity.ok(order);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }

    // Get all orders for a specific user
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable String userId) {
        return orderService.getOrdersByUser(userId);
    }

    // Get all orders for a specific vendor
    @GetMapping("/vendor/{vendorId}")
    public List<Order> getOrdersByVendor(@PathVariable String vendorId) {
        return orderService.getOrdersByVendor(vendorId);
    }

    // Get all orders (admin/debug)
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getOrdersByUser(null);
        // Alternatively, create a dedicated getAllOrders() method for clarity
    }
}
