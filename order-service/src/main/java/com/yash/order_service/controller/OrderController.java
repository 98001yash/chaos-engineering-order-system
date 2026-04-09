package com.yash.order_service.controller;


import com.yash.order_service.clients.PaymentClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    public String createOrder() {
        System.out.println("Calling payment service...");
        String paymentResponse = callPaymentService();
        return "Order Success | " + paymentResponse;
    }

    @Retryable(
            value = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    @CircuitBreaker(name = "paymentService", fallbackMethod = "fallbackMethod")
    @Bulkhead(name = "paymentService", type = Bulkhead.Type.THREADPOOL)
    public String callPaymentService() {
        return paymentClient.processPayment();
    }

    public String fallbackMethod(Throwable t) {
        return "Payment service is down. Order placed in pending state.";
    }
}