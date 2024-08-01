package com.backendVn.SWP.controllers;

import com.backendVn.SWP.dtos.request.DesignFeedBackRequest;
import com.backendVn.SWP.dtos.response.ApiResponse;
import com.backendVn.SWP.dtos.response.DesignResponse;
import com.backendVn.SWP.dtos.response.RequestOrderResponse;
import com.backendVn.SWP.dtos.response.UserResponse;
import com.backendVn.SWP.services.RequestOrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request-orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestOrderController {
    RequestOrderService requestOrderService;

    @PostMapping("/{id}")
    public ApiResponse<RequestOrderResponse> createRequestOrder(@PathVariable Integer id) {
        RequestOrderResponse requestOrderResponse = requestOrderService.createRequestOrder(id);
        return ApiResponse.<RequestOrderResponse>builder()
                .result(requestOrderResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRequestOrder(@PathVariable Integer id) {
        requestOrderService.deleteRequestOrder(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping
    public ApiResponse<List<RequestOrderResponse>> getAllRequestOrders() {
        List<RequestOrderResponse> requestOrderResponses = requestOrderService.getAllRequestOrders();
        return ApiResponse.<List<RequestOrderResponse>>builder()
                .result(requestOrderResponses)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<RequestOrderResponse> getRequestOrderById(@PathVariable Integer id) {
        RequestOrderResponse requestOrderResponse = requestOrderService.getRequestOrderById(id);
        return ApiResponse.<RequestOrderResponse>builder()
                .result(requestOrderResponse)
                .build();
    }

    @PutMapping("/{requestOrderId}/assign-design/{designId}")
    public ApiResponse<RequestOrderResponse> assignDesignToRequestOrder(@PathVariable Integer requestOrderId, @PathVariable Integer designId) {
        return ApiResponse.<RequestOrderResponse>builder()
                .result(requestOrderService.updateRequestOrderWithDesign(requestOrderId, designId))
                .build();
    }

    @PutMapping("/{requestOrderId}/{designStaffId}/{productionStaffId}")
    public ApiResponse<RequestOrderResponse> assignWork(@PathVariable Integer requestOrderId, @PathVariable Integer designStaffId, @PathVariable Integer productionStaffId){
        return ApiResponse.<RequestOrderResponse>builder()
                .result(requestOrderService.assignWork(requestOrderId, designStaffId, productionStaffId))
                .build();
    }

    @GetMapping("/getUserByRole/{role}")
    public ApiResponse<List<UserResponse>> getUserByRole(@PathVariable String role) {
        return ApiResponse.<List<UserResponse>>builder()
                .result(requestOrderService.getUserByRole(role))
                .build();
    }

    @GetMapping("/getOrderForDesigner/{designerStaffId}")
    public ApiResponse<List<RequestOrderResponse>> getOrderForDesigner(@PathVariable Integer designerStaffId) {
        return ApiResponse.<List<RequestOrderResponse>>builder()
                .result(requestOrderService.getRequestOrdersByDesign(designerStaffId))
                .build();
    }

    @GetMapping("/getOrderByRequestIdForCustomer/{requestId}")
    public ApiResponse<RequestOrderResponse> getOrderByRequestIdForCustomer(@PathVariable Integer requestId){
        return ApiResponse.<RequestOrderResponse>builder()
                .result(requestOrderService.getOrderByRequestIdForCustomer(requestId))
                .build();
    }

    @PutMapping("/acceptDesign/{designId}")
    public ApiResponse<RequestOrderResponse> acceptDesign(@PathVariable Integer designId) {
        return ApiResponse.<RequestOrderResponse>builder()
                .result(requestOrderService.acceptDesign(designId))
                .build();
    }

    @GetMapping("/getAllNewRequestOrder")
    public ApiResponse<List<RequestOrderResponse>> getAllNewRequestOrder() {
        return ApiResponse.<List<RequestOrderResponse>>builder()
                .result(requestOrderService.getAllNewRequestOrder())
                .build();
    }

    @GetMapping("/getAllOrderByProductionStaff/{staffId}")
    public ApiResponse<List<RequestOrderResponse>> getAllOrderByProductionStaff(@PathVariable Integer staffId) {
        return ApiResponse.<List<RequestOrderResponse>>builder()
                .result(requestOrderService.getRequestOrdersByProductionStaffId(staffId))
                .build();
    }
}
