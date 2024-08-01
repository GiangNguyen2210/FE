package com.backendVn.SWP.controllers;

import com.backendVn.SWP.dtos.request.MaterialRequest;
import com.backendVn.SWP.dtos.response.ApiResponse;
import com.backendVn.SWP.dtos.response.MaterialResponse;
import com.backendVn.SWP.services.MaterialService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialController {
    MaterialService materialService;

    @PostMapping
    public ApiResponse<MaterialResponse> createMaterial(@RequestBody MaterialRequest materialRequest) {
        return ApiResponse.<MaterialResponse>builder()
                .result(materialService.createMaterial(materialRequest))
                .build();
    }

    @PutMapping("/{materialID}")
    public ApiResponse<MaterialResponse> updateMaterial(@PathVariable Integer materialID, @RequestBody MaterialRequest materialRequest) {
        return  ApiResponse.<MaterialResponse>builder()
                .result(materialService.updateMaterial(materialRequest, materialID))
                .build();
    }

    @GetMapping("/notGold")
    public ApiResponse<List<MaterialResponse>> getMaterialNotGold(){
        return ApiResponse.<List<MaterialResponse>>builder()
                .result(materialService.getMaterialNotGold())
                .build();
    }

    @DeleteMapping("/{materialID}")
    public void deleteMaterial(@PathVariable Integer materialID){
        materialService.deleteMaterial(materialID);
    }

    }

