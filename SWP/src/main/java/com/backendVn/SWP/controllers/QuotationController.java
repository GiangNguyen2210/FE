package com.backendVn.SWP.controllers;

import com.backendVn.SWP.dtos.request.QuotationCreationRequest;
import com.backendVn.SWP.dtos.response.ApiResponse;
import com.backendVn.SWP.dtos.response.AutoPricingResponse;
import com.backendVn.SWP.dtos.response.QuotationResponse;
import com.backendVn.SWP.services.QuotationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotation")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuotationController {
    QuotationService quotationService;

    @PostMapping("/{requestId}")
    public ApiResponse<QuotationResponse> createQuotation(@PathVariable Integer requestId, @RequestBody QuotationCreationRequest quotationCreationRequest){
        QuotationResponse quotationResponse = quotationService.createQuotation(quotationCreationRequest, requestId);
        return ApiResponse.<QuotationResponse>builder()
                .result(quotationResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<List<QuotationResponse>> getQuotation(){
        return ApiResponse.<List<QuotationResponse>>builder()
                .result(quotationService.getAllQuotation())
                .build();
    }

    @PutMapping("/update/{Id}")
    public ApiResponse<QuotationResponse> updateQuotation(@PathVariable Integer Id){
        return ApiResponse.<QuotationResponse>builder()
                .result(quotationService.updateQuotation(Id))
                .build();
    }

    @GetMapping("/{requestId}")
    public ApiResponse<QuotationResponse> getQuotationByRequestId(@PathVariable Integer requestId){
        return ApiResponse.<QuotationResponse>builder()
                .result(quotationService.getQuotationById(requestId))
                .build();
    }

    @GetMapping("/autoPricing/{requestId}")
    public ApiResponse<AutoPricingResponse> autoPricing(@PathVariable Integer requestId){
        return ApiResponse.<AutoPricingResponse>builder()
                .result(quotationService.getAutoPricing(requestId))
                .build();
    }

    @PutMapping("denyFromManager/{quotationId}")
    public ApiResponse<QuotationResponse> denyFromManager(@PathVariable Integer quotationId, @RequestBody String deniedReason){
        return ApiResponse.<QuotationResponse>builder()
                .result(quotationService.denyFromManager(quotationId, deniedReason))
                .build();
    }
}
