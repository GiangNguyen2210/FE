package com.backendVn.SWP.controllers;

import com.backendVn.SWP.dtos.request.InvoiceUpdateRequest;
import com.backendVn.SWP.dtos.response.ApiResponse;
import com.backendVn.SWP.dtos.response.InvoiceInfor;
import com.backendVn.SWP.dtos.response.InvoiceResponse;
import com.backendVn.SWP.services.InvoiceService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceController {
    InvoiceService invoiceService;

    @PostMapping("/{requestId}")
    public ApiResponse<InvoiceResponse> createInvoice(@PathVariable Integer requestId) {
        InvoiceResponse invoiceResponse = invoiceService.createInvoice(requestId);
        return ApiResponse.<InvoiceResponse>builder()
                .result(invoiceResponse)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<InvoiceResponse> updateInvoice(@PathVariable Integer id, @RequestBody @Valid InvoiceUpdateRequest invoiceUpdateRequest) {
        InvoiceResponse invoiceResponse = invoiceService.updateInvoice(id, invoiceUpdateRequest);
        return ApiResponse.<InvoiceResponse>builder()
                .result(invoiceResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteInvoice(@PathVariable Integer id) {
        invoiceService.deleteInvoice(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping
    public ApiResponse<List<InvoiceResponse>> getAllInvoices() {
        List<InvoiceResponse> invoiceResponses = invoiceService.getAllInvoices();
        return ApiResponse.<List<InvoiceResponse>>builder()
                .result(invoiceResponses)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<InvoiceResponse> getInvoiceById(@PathVariable Integer id) {
        InvoiceResponse invoiceResponse = invoiceService.getInvoiceById(id);
        return ApiResponse.<InvoiceResponse>builder()
                .result(invoiceResponse)
                .build();
    }

    @GetMapping("/getInvoiceInfor/{requestOrderId}")
    public ApiResponse<InvoiceInfor> getInvoiceInfor(@PathVariable Integer requestOrderId){
        return ApiResponse.<InvoiceInfor>builder()
                .result(invoiceService.getAddInvoiceInfor(requestOrderId))
                .build();
    }
}
