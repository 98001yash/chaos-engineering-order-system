package com.yash.order_service.clients;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "payment-service", url = "${payment-service.url}")
public interface PaymentClient {

    @GetMapping("/pay")
    String processPayment();
}