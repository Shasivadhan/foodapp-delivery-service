package com.foodapp.order.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private String menuItemId;
    private String name;
    private double price;
    private int quantity;
}
