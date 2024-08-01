package com.backendVn.SWP.controllers;

import com.backendVn.SWP.dtos.response.ApiResponse;
import com.backendVn.SWP.dtos.response.WarrantyCardResponse;
import com.backendVn.SWP.services.WarrantyCardService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/warranty-cards")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarrantyCardController {
    WarrantyCardService warrantyCardService;

    @PostMapping("/{id}")
    public ApiResponse<WarrantyCardResponse> createWarrantyCard(@PathVariable Integer id) {
        WarrantyCardResponse warrantyCardResponse = warrantyCardService.createWarrantyCard(id);
        return ApiResponse.<WarrantyCardResponse>builder()
                .result(warrantyCardResponse)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<WarrantyCardResponse> updateWarrantyCard(@PathVariable Integer id ) {
        WarrantyCardResponse warrantyCardResponse = warrantyCardService.updateWarrantyCard(id);
        return ApiResponse.<WarrantyCardResponse>builder()
                .result(warrantyCardResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteWarrantyCard(@PathVariable Integer id) {
        warrantyCardService.deleteWarrantyCard(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping
    public ApiResponse<List<WarrantyCardResponse>> getAllWarrantyCards() {
        return ApiResponse.<List<WarrantyCardResponse>>builder()
                .result(warrantyCardService.getAllWarrantyCards())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<WarrantyCardResponse> getWarrantyCardById(@PathVariable Integer id) {
        WarrantyCardResponse warrantyCardResponse = warrantyCardService.getWarrantyCardById(id);
        return ApiResponse.<WarrantyCardResponse>builder()
                .result(warrantyCardResponse)
                .build();
    }
}

