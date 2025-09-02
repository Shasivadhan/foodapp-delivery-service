package com.foodapp.order.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private String userId;          // App user
    private String accountNumber;   // Bank account number for payment
}
