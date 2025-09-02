package com.foodapp.order.feign;

import com.foodapp.order.dto.FundTransferRequest;
import com.foodapp.order.dto.FundTransferResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bank-service", url = "http://localhost:8083/api")
public interface BankClient {
    @PostMapping(value = "/funds/transfer", consumes = "application/json")
    FundTransferResponse transferFunds(@RequestBody FundTransferRequest request);
}
