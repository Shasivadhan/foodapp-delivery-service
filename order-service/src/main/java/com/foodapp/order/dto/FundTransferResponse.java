package com.foodapp.order.dto;

import lombok.Data;

@Data
public class FundTransferResponse {
    private String status;       // e.g. "SUCCESS" or "FAILED"
    private String transactionId;  // bank-generated reference
    private String message;        // optional: error/success message
}
