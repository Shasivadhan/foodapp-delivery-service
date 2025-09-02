package com.foodapp.order.entity;

import lombok.Data;

@Data   // Lombok generates getters, setters, toString, equals, hashCode
public class OrderItem {
    private String menuItemId;
    private String name;
    private double price;
    private int quantity;
}
