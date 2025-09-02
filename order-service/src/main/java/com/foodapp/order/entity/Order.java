package com.foodapp.order.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data   // Lombok generates getters, setters, toString, equals, hashCode
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private String vendorId;
    private List<OrderItem> items;
    private double total;
    private String status;      // NEW, PAID, FAILED, DELIVERED
    private String paymentRef;
    private LocalDateTime createdAt;
}
