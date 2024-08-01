package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.response.PaymentResponse;
import com.backendVn.SWP.entities.Payment;
import com.backendVn.SWP.entities.Quotation;
import com.backendVn.SWP.entities.Request;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.mappers.PaymentMapper;
import com.backendVn.SWP.repositories.PaymentRepository;
import com.backendVn.SWP.repositories.QuotationRepository;
import com.backendVn.SWP.repositories.RequestOrderRepository;
import com.backendVn.SWP.repositories.RequestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {

    RequestRepository requestRepository;
    QuotationRepository quotationRepository;
    PaymentRepository paymentRepository;
    RequestOrderRepository requestOrderRepository;
    PaymentMapper paymentMapper;

//    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public PaymentResponse createDeposit(Integer requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
        List<Quotation> quotations = quotationRepository.findByRequestID(request)
                .orElseThrow(() -> new AppException(ErrorCode.QUOTATION_NOT_FOUND));
        quotations.sort(Comparator.comparing(Quotation::getCreatedAt));
        List<Payment> payments = paymentRepository.findByRequestIDAndPaymentType(request, "Deposit")
                .orElse(new ArrayList<>());

        if (!payments.isEmpty()){
            return null;
        }

        Payment payment = Payment.builder()
                .paymentType("Deposit")
                .amount(quotations.getLast().getCost().divide(BigDecimal.valueOf(2)))
                .status("Unpaid")
                .createdAt(Instant.now())
                .requestID(request)
                .build();

        paymentRepository.save(payment);

        return paymentMapper.toPaymentResponse(payment);
    }

    public PaymentResponse createPayment(Integer requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));
        List<Quotation> quotations = quotationRepository.findByRequestID(request)
                .orElseThrow(() -> new AppException(ErrorCode.QUOTATION_NOT_FOUND));
        quotations.sort(Comparator.comparing(Quotation::getCreatedAt));
        List<Payment> payments = paymentRepository.findByRequestIDAndPaymentType(request, "Payment")
                .orElse(new ArrayList<>());

        if (!payments.isEmpty()){
            return null;
        }

        Payment payment = Payment.builder()
                .paymentType("Payment")
                .createdAt(Instant.now())
                .amount(quotations.getLast().getCost().divide(BigDecimal.valueOf(2)))
                .status("Unpaid")
                .requestID(request)
                .build();

        paymentRepository.save(payment);

        return paymentMapper.toPaymentResponse(payment);
    }

    public PaymentResponse makePayment(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        payment.setStatus("Paid");
        payment.setPaymentDate(Instant.now());

        if (payment.getPaymentType().equals("Deposit")) {
            payment.getRequestID().setStatus("Ordering");
            requestRepository.save(payment.getRequestID());
        } else {
            RequestOrder requestOrder = requestOrderRepository.findByRequestID(payment.getRequestID())
                    .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));
            requestOrder.setStatus("finished");
            requestOrder.setEndAt(Instant.now());
            requestOrderRepository.save(requestOrder);
            requestOrder.getRequestID().setStatus("finished");
            requestOrder.getRequestID().setEndAt(Instant.now());
            requestRepository.save(requestOrder.getRequestID());
        }

        paymentRepository.save(payment);

        return paymentMapper.toPaymentResponse(payment);
    }
    
    public PaymentResponse getPayment(Integer requestId, String paymentType) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_NOT_FOUND));

        List<Payment> payments = paymentRepository.findByRequestIDAndPaymentType(request, paymentType)
                .orElse(new ArrayList<>());

        if (payments.isEmpty()){
            return null;
        }

        payments.sort(Comparator.comparing(Payment::getCreatedAt));
        return paymentMapper.toPaymentResponse(payments.getLast());
    }
}
