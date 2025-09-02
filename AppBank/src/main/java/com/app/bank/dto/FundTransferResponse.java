package com.app.bank.dto;

public class FundTransferResponse {
    private String status;
    private String transactionId;
    private String message;

    // Getters & Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
