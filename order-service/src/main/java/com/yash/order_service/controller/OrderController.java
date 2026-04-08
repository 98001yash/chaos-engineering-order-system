package com.yash.order_service.controller;


import com.yash.order_service.clients.PaymentClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final PaymentClient paymentClient;

    public OrderController(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @GetMapping("/order")
    @Retryable(
            value = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    public String createOrder() {
        try {
            System.out.println("Calling payment service...");
            String paymentResponse = paymentClient.processPayment();
            return "Order Success  | " + paymentResponse;

        } catch (Exception e) {
            System.out.println("Payment failed, retrying...");
            throw e; // IMPORTANT
        }
    }
}