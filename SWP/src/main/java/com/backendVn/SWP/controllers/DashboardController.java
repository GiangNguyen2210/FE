package com.backendVn.SWP.controllers;

import com.backendVn.SWP.dtos.response.*;
import com.backendVn.SWP.services.DashboardService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardController {
    DashboardService dashboardService;

    @GetMapping("/monthly-order-count")
    public ApiResponse<List<MonthlyCountResponse>> getMonthlyOrderCount(
            @RequestParam("year") int year,
            @RequestParam("startMonth") int startMonth,
            @RequestParam("endMonth") int endMonth) {
        return ApiResponse.<List<MonthlyCountResponse>>builder()
                .result(dashboardService.calculateMonthlyOrderCount(year, startMonth, endMonth))
                .build();
    }

    @GetMapping("/monthly-request-count")
    public ApiResponse<List<MonthlyCountResponse>> getMonthlyRequestCount(
            @RequestParam("year") int year,
            @RequestParam("startMonth") int startMonth,
            @RequestParam("endMonth") int endMonth) {
        return ApiResponse.<List<MonthlyCountResponse>>builder()
                .result(dashboardService.calculateMonthlyRequestCount(year, startMonth, endMonth))
                .build();
    }

    @GetMapping("/monthly-order-complete-count")
    public ApiResponse<List<MonthlyCountResponse>> getMonthlyOrderCompleteCount(
            @RequestParam("year") int year,
            @RequestParam("startMonth") int startMonth,
            @RequestParam("endMonth") int endMonth) {
        return ApiResponse.<List<MonthlyCountResponse>>builder()
                .result(dashboardService.calculateMonthlyOrderCompleteCount(year, startMonth, endMonth))
                .build();
    }

    @GetMapping("/monthly-profit-count")
    public ApiResponse<List<MonthlyIncomeResponse>> getMonthlyProfitCount(
            @RequestParam("year") int year,
            @RequestParam("startMonth") int startMonth,
            @RequestParam("endMonth") int endMonth) {
        return ApiResponse.<List<MonthlyIncomeResponse>>builder()
                .result(dashboardService.calculateMonthlyProfitCount(year, startMonth, endMonth))
                .build();
    }

    @GetMapping("/latest-transactions")
    public ApiResponse<List<TransactionResponse>> getLatestTransactions() {
        return ApiResponse.<List<TransactionResponse>>builder()
                .result(dashboardService.getLatestTransactions())
                .build();
    }

    @GetMapping("/monthly-revenue")
    public ApiResponse<List<MonthlyIncomeResponse>> getMonthlyRevenue(
            @RequestParam("year") int year,
            @RequestParam("startMonth") int startMonth,
            @RequestParam("endMonth") int endMonth) {
        return ApiResponse.<List<MonthlyIncomeResponse>>builder()
                .result(dashboardService.calculateMonthlyRevenue(year, startMonth, endMonth))
                .build();
    }

    @GetMapping("/top-selling-products")
    public ApiResponse<List<SellingProductResponse>> getTopSellingProducts() {
        return ApiResponse.<List<SellingProductResponse>>builder()
                .result(dashboardService.sellingProducts())
                .build();
    }
}
