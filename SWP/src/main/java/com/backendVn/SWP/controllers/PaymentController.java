package com.backendVn.SWP.controllers;

import com.backendVn.SWP.dtos.response.ApiResponse;
import com.backendVn.SWP.dtos.response.PaymentResponse;
import com.backendVn.SWP.services.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;

    @PostMapping("/{requestId}")
    public ApiResponse<PaymentResponse> createDeposit(@PathVariable Integer requestId) {
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.createDeposit(requestId))
                .build();
    }

    @PostMapping("/createPayment/{requestId}")
    public ApiResponse<PaymentResponse> createPayment(@PathVariable Integer requestId) {
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.createPayment(requestId))
                .build();
    }

    @PutMapping("/makePayment/{paymentId}")
    public ApiResponse<PaymentResponse> makePayment(@PathVariable Integer paymentId) {
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.makePayment(paymentId))
                .build();
    }

    @GetMapping("/getPayment/{requestId}/{paymentType}")
    public ApiResponse<PaymentResponse> getPayment(@PathVariable Integer requestId, @PathVariable String paymentType){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.getPayment(requestId, paymentType))
                .build();
    }
}
