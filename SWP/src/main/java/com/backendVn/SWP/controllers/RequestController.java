package com.backendVn.SWP.controllers;


import com.backendVn.SWP.dtos.request.CompanyDesignModifyRequest;
import com.backendVn.SWP.dtos.request.RequestCreationRequestForCustomerDesign;
import com.backendVn.SWP.dtos.response.ApiResponse;
import com.backendVn.SWP.dtos.response.DesignResponse;
import com.backendVn.SWP.dtos.response.RequestResponse;
import com.backendVn.SWP.dtos.response.UserResponse;
import com.backendVn.SWP.services.RequestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestController {
    RequestService requestService;

    @PostMapping("/{userId}")
    public ApiResponse<RequestResponse> createRequest(@PathVariable Integer userId, @RequestBody @Valid RequestCreationRequestForCustomerDesign requestCreationRequestForCustomerDesign) {
        RequestResponse requestResponse = requestService.createRequest(requestCreationRequestForCustomerDesign, userId);
        return ApiResponse.<RequestResponse>builder()
                .result(requestResponse)
                .build();
    }

    @PutMapping("/{requestId}")
    public ApiResponse<RequestResponse> updateRequestByCustomer(@PathVariable Integer requestId, @RequestBody @Valid RequestCreationRequestForCustomerDesign requestCreationRequestForCustomerDesign) {
        RequestResponse requestResponse = requestService.updateRequestByCustomer(requestId, requestCreationRequestForCustomerDesign);
        return ApiResponse.<RequestResponse>builder()
                .result(requestResponse)
                .build();
    }

    @PutMapping("/sales/{requestId}")
    public ApiResponse<RequestResponse> updateRequestBySales(@PathVariable Integer requestId) {
        return ApiResponse.<RequestResponse>builder()
                .result(requestService.updateRequestBySales(requestId))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RequestResponse>> getAllRequests() {
        List<RequestResponse> requestResponses = requestService.getAllRequests();
        return ApiResponse.<List<RequestResponse>>builder()
                .result(requestResponses)
                .build();
    }

    @GetMapping("/{requestId}")
    public ApiResponse<RequestResponse> getRequestById(@PathVariable Integer requestId) {
        RequestResponse requestResponse = requestService.getRequestById(requestId);
        return ApiResponse.<RequestResponse>builder()
                .result(requestResponse)
                .build();
    }

    @GetMapping("/customer/{customerId}")
    public ApiResponse<List<RequestResponse>> getRequestsByCustomerId(@PathVariable Integer customerId) {
        List<RequestResponse> requestResponses = requestService.getRequestsByCustomerId(customerId);
        return ApiResponse.<List<RequestResponse>>builder()
                .result(requestResponses)
                .build();
    }

    @PutMapping("/deleteRequest/{id}")
    public void deleteRequest(@PathVariable Integer id) {
        requestService.deleteRequest(id);
    }

    @GetMapping("/unrecievedRequest")
    public ApiResponse<List<RequestResponse>> getUnrecievedRequests() {
        return ApiResponse.<List<RequestResponse>>builder()
                .result(requestService.getUnrecievedRequests())
                .build();
    }

    @GetMapping("/sales/{saleStaffId}")
    public ApiResponse<List<RequestResponse>> getRequestsBySaleStaffId(@PathVariable Integer saleStaffId) {
        return ApiResponse.<List<RequestResponse>>builder()
                .result(requestService.getRequestBySaleStaffId(saleStaffId))
                .build();
    }

    @GetMapping("/customerInfo/{customerId}")
    public ApiResponse<UserResponse> getCustomerInfo(@PathVariable Integer customerId){
        return ApiResponse.<UserResponse>builder()
                .result(requestService.getUserById(customerId))
                .build();
    }

    @PutMapping("/approveQuotationFromCustomer/{requestId}")
    public ApiResponse<RequestResponse> approveQuotationFromCustomer(@PathVariable Integer requestId) {
        return ApiResponse.<RequestResponse>builder()
                .result(requestService.approveQuotationFromCustomer(requestId))
                .build();
    }

    @PutMapping("/denyQuotationFromCustomer/{requestId}")
    public ApiResponse<RequestResponse> denyQuotationFromCustomer(@PathVariable Integer requestId, @RequestBody String deniedReason) {
        return ApiResponse.<RequestResponse>builder()
                .result(requestService.denyQuotationFromCustomer(requestId, deniedReason))
                .build();
    }

    @GetMapping("/getPendingQuotationRequest")
    public ApiResponse<List<RequestResponse>> getPendingQuotationRequests() {
        return ApiResponse.<List<RequestResponse>>builder()
                .result(requestService.getListOfRequestQuotations())
                .build();
    }

    @PostMapping("/requestCompanyDesign/{userId}/{designId}")
    public ApiResponse<RequestResponse> createRequestCompanyDesign(@PathVariable Integer userId, @PathVariable Integer designId) {
        return ApiResponse.<RequestResponse>builder()
                .result(requestService.createRequestWithCompanyDesign(userId,designId))
                .build();
    }

    @PutMapping("/sendRequest/{requestId}")
    public ApiResponse<RequestResponse> sendRequest(@PathVariable Integer requestId) {
        return  ApiResponse.<RequestResponse>builder()
                .result(requestService.sendRequest(requestId))
                .build();
    }


}
