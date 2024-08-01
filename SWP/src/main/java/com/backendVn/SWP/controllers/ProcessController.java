package com.backendVn.SWP.controllers;

import com.backendVn.SWP.dtos.request.ProcessUpdateRequest;
import com.backendVn.SWP.dtos.response.ApiResponse;
import com.backendVn.SWP.dtos.response.ProcessResponse;
import com.backendVn.SWP.services.ProcessService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProcessController {
    ProcessService processService;

    @GetMapping
    public ApiResponse<List<ProcessResponse>> getAllProcess(){
        return ApiResponse.<List<ProcessResponse>>builder()
                .result(processService.getAllProcesses())
                .build();
    }

    @PostMapping("/{requestOrderId}/{productionStaffId}")
    public ApiResponse<ProcessResponse> createProcess(@PathVariable Integer requestOrderId, @PathVariable Integer productionStaffId){
        return ApiResponse.<ProcessResponse>builder()
                .result(processService.createProcess(requestOrderId, productionStaffId))
                .build();
    }

    @PutMapping("/{requestOrderId}")
    public ApiResponse<ProcessResponse> updateProcess(@PathVariable Integer requestOrderId, @RequestBody @Valid ProcessUpdateRequest processUpdateRequest){
        return ApiResponse.<ProcessResponse>builder()
                .result(processService.updateProcess(requestOrderId, processUpdateRequest))
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<ProcessResponse>> getProcessesByUserId(@PathVariable Integer userId) {
        return ApiResponse.<List<ProcessResponse>>builder()
                .result(processService.getProcessesByUserId(userId))
                .build();
    }

    @GetMapping("/getMyProcesses")
    public ApiResponse<List<ProcessResponse>> getMyProcesses() {
        return ApiResponse.<List<ProcessResponse>>builder()
                .result(processService.getMyProcesses())
                .build();
    }

    @GetMapping("/getProcessByRequestOrderId/{orderId}")
    public ApiResponse<ProcessResponse> getProcessByRequestOrderId(@PathVariable Integer orderId) {
        return ApiResponse.<ProcessResponse>builder()
                .result(processService.getProcessByRequestOrderId(orderId))
                .build();
    }
}
