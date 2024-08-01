package com.backendVn.SWP.controllers;

import com.backendVn.SWP.dtos.request.InvoiceDetailUpdateRequest;
import com.backendVn.SWP.dtos.response.ApiResponse;
import com.backendVn.SWP.dtos.response.InvoiceDetailResponse;
import com.backendVn.SWP.services.InvoiceDetailService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice-details")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceDetailController {
    InvoiceDetailService invoiceDetailService;

    @PostMapping("/{requestOrderId}")
    public ApiResponse<List<InvoiceDetailResponse>> createInvoiceDetail(@PathVariable Integer requestOrderId) {
        List<InvoiceDetailResponse> invoiceDetailResponse = invoiceDetailService.createInvoiceDetail(requestOrderId);
        return ApiResponse.<List<InvoiceDetailResponse>>builder()
                .result(invoiceDetailResponse)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<InvoiceDetailResponse> updateInvoiceDetail(@PathVariable Integer id, @RequestBody @Valid InvoiceDetailUpdateRequest invoiceDetailUpdateRequest) {
        InvoiceDetailResponse invoiceDetailResponse = invoiceDetailService.updateInvoiceDetail(id, invoiceDetailUpdateRequest);
        return ApiResponse.<InvoiceDetailResponse>builder()
                .result(invoiceDetailResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteInvoiceDetail(@PathVariable Integer id) {
        invoiceDetailService.deleteInvoiceDetail(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping
    public ApiResponse<List<InvoiceDetailResponse>> getAllInvoiceDetails() {
        List<InvoiceDetailResponse> invoiceDetailResponses = invoiceDetailService.getAllInvoiceDetails();
        return ApiResponse.<List<InvoiceDetailResponse>>builder()
                .result(invoiceDetailResponses)
                .build();
    }

    @GetMapping("/getInvoiceDetailByInvoiceId/{invoiceId}")
    public ApiResponse<List<InvoiceDetailResponse>> getInvoiceDetailById(@PathVariable Integer invoiceId) {
        List<InvoiceDetailResponse> invoiceDetailResponse = invoiceDetailService.getInvoiceDetailByInvoiceId(invoiceId);
        return ApiResponse.<List<InvoiceDetailResponse>>builder()
                .result(invoiceDetailResponse)
                .build();
    }
}
