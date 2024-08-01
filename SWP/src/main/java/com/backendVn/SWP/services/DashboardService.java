package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.response.*;
import com.backendVn.SWP.entities.*;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.mappers.PaymentMapper;
import com.backendVn.SWP.mappers.RequestMapper;
import com.backendVn.SWP.mappers.UserMapper;
import com.backendVn.SWP.repositories.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardService {
    InvoiceRepository invoiceRepository;
    QuotationRepository quotationRepository;
    RequestRepository requestRepository;
    PaymentRepository paymentRepository;
    UserMapper userMapper;
    RequestMapper requestMapper;
    PaymentMapper paymentMapper;

    public List<MonthlyIncomeResponse> calculateMonthlyRevenue(int year, int startMonth, int endMonth) {
        List<MonthlyIncomeResponse> monthlyRevenues = new ArrayList<>();

        for (int month = startMonth; month <= endMonth; month++) {
            Instant startDate = Year.of(year).atMonth(month).atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant endDate = Year.of(year).atMonth(month).atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();

            BigDecimal totalRevenue = calculateTotalRevenue(startDate, endDate);
            monthlyRevenues.add(new MonthlyIncomeResponse(month, totalRevenue));
        }

        return monthlyRevenues;
    }

    public BigDecimal calculateTotalRevenue(Instant startDate, Instant endDate) {
        List<Invoice> invoices = requestRepository.findByCreatedAtBetweenAndStatus(startDate, endDate, "finished")
                .stream()
                .map(request -> invoiceRepository.findByRequestID(request)
                        .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND)))
                .toList();

        return invoices.stream()
                .map(Invoice::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalProfit(Instant startDate, Instant endDate) {
        BigDecimal revenue = calculateTotalRevenue(startDate, endDate);

        List<Request> requests = requestRepository.findByCreatedAtBetweenAndStatus(startDate, endDate, "finished");

        BigDecimal totalCapitalCost = requests.stream()
                .map(request -> quotationRepository.findTopByRequestIDOrderByCreatedAtDesc(request)
                        .orElseThrow(() -> new AppException(ErrorCode.QUOTATION_NOT_FOUND))
                        .getCapitalCost())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return revenue.subtract(totalCapitalCost);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_MANAGER')")
    public List<MonthlyCountResponse> calculateMonthlyRequestCount(int year, int startMonth, int endMonth) {
        List<MonthlyCountResponse> monthlyRequestCounts = new ArrayList<>();

        for (int month = startMonth; month <= endMonth; month++) {
            Instant startDate = Year.of(year).atMonth(month).atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant endDate = Year.of(year).atMonth(month).atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();

            Long requestCount = countRequests(startDate, endDate);
            monthlyRequestCounts.add(new MonthlyCountResponse(month, requestCount));
        }

        return monthlyRequestCounts;
    }

    private Long countRequests(Instant startDate, Instant endDate) {
        return requestRepository.countByCreatedAtBetween(startDate, endDate);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_MANAGER')")
    public List<MonthlyCountResponse> calculateMonthlyOrderCompleteCount(int year, int startMonth, int endMonth) {
        List<MonthlyCountResponse> monthlyOrderCompleteCounts = new ArrayList<>();

        for (int month = startMonth; month <= endMonth; month++) {
            Instant startDate = Year.of(year).atMonth(month).atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant endDate = Year.of(year).atMonth(month).atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();

            Long orderCompleteCount = countOrdersComplete(startDate, endDate);
            monthlyOrderCompleteCounts.add(new MonthlyCountResponse(month, orderCompleteCount));
        }

        return monthlyOrderCompleteCounts;
    }

    private Long countOrdersComplete(Instant startDate, Instant endDate) {
        return invoiceRepository.countByCreatedAtBetween(startDate, endDate);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_MANAGER')")
    public List<MonthlyCountResponse> calculateMonthlyOrderCount(int year, int startMonth, int endMonth) {
        List<MonthlyCountResponse> monthlyOrderCounts = new ArrayList<>();

        for (int month = startMonth; month <= endMonth; month++) {
            Instant startDate = Year.of(year).atMonth(month).atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant endDate = Year.of(year).atMonth(month).atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();

            Long orderCount = countOrders(startDate, endDate);
            monthlyOrderCounts.add(new MonthlyCountResponse(month, orderCount));
        }

        return monthlyOrderCounts;
    }

    public Long countOrders(Instant startDate, Instant endDate) {
        return (long) requestRepository.findByCreatedAtBetweenAndStatus(startDate, endDate, "finished")
                .size();
    }

    public List<SellingProductResponse> sellingProducts() {
        List<Request> requests = requestRepository.findAllByCompanyDesignIsNotNullAndStatus("finished");
        Map<Design, Integer> map = new HashMap<>();

        // Count the occurrences of each design
        for (Request request : requests) {
            Design design = request.getCompanyDesign();
            map.put(design, map.getOrDefault(design, 0) + 1);
        }

        // Sort the map entries by value (order count) in descending order
        List<Map.Entry<Design, Integer>> sortedEntries = map.entrySet().stream()
                .sorted(Map.Entry.<Design, Integer>comparingByValue().reversed())
                .toList();

        List<SellingProductResponse> sellingProductResponses = new ArrayList<>();

        for (Map.Entry<Design, Integer> entry : sortedEntries) {
            Design design = entry.getKey();
            log.info("Processing design name {}, ID{}", design.getDesignName(), design.getId());
            Integer orderCount = entry.getValue();

            BigDecimal price;
            Request request = null;
            try {
                request = requestRepository.findFirstByCompanyDesignAndStatus(design, "finished")
                        .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
                price = invoiceRepository.findByRequestID(request)
                        .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND))
                        .getTotalCost();
            } catch (AppException e) {
                log.error("No invoice found for request ID{}", request.getId());
                throw e;
            }

            SellingProductResponse sellingProductResponse = new SellingProductResponse();
            sellingProductResponse.setId(design.getId());
            sellingProductResponse.setDesignName(design.getDesignName());
            sellingProductResponse.setOrder_count(orderCount);
            sellingProductResponse.setPrice(price);

            sellingProductResponses.add(sellingProductResponse);
        }

        return sellingProductResponses;
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_MANAGER')")
    public List<TransactionResponse> getLatestTransactions() {
        List<Payment> payments = paymentRepository.findTop10ByOrderByPaymentDateDesc();
        List<TransactionResponse> transactionResponses = new ArrayList<>();

        for (Payment payment : payments) {
            Request request = payment.getRequestID();
            User user = request.getCustomerID();

            TransactionResponse transactionResponse;

            transactionResponse = requestMapper.toTransactionResponse(request);
            paymentMapper.updateTransactionResponseFromPayment(payment, transactionResponse);
            userMapper.updateTransactionResponseFromUser(user, transactionResponse);
            transactionResponse.setId(request.getId());
            transactionResponses.add(transactionResponse);
        }
        return transactionResponses;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_MANAGER')")
    public List<MonthlyIncomeResponse> calculateMonthlyProfitCount(int year, int startMonth, int endMonth) {
        List<MonthlyIncomeResponse> monthlyProfitCounts = new ArrayList<>();

        for (int month = startMonth; month <= endMonth; month++) {
            Instant startDate = Year.of(year).atMonth(month).atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant endDate = Year.of(year).atMonth(month).atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();

            BigDecimal totalProfit = calculateTotalProfit(startDate, endDate);
            monthlyProfitCounts.add(new MonthlyIncomeResponse(month, totalProfit));
        }

        return monthlyProfitCounts;
    }
}
