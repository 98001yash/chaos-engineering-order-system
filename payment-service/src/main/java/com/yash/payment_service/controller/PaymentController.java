package com.yash.payment_service.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class PaymentController {

    private final Random random = new Random();

    @GetMapping("/pay")
    public String processPayment() throws InterruptedException {

        int chance = random.nextInt(100);

        // 40% failure
        if (chance < 40) {
            throw new RuntimeException("Payment Failed.....!!!");
        }

        // 30% delay
        if (chance < 70) {
            Thread.sleep(3000);
        }

        return "Payment Successful";
    }
}