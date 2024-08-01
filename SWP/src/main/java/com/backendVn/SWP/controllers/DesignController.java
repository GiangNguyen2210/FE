package com.backendVn.SWP.controllers;


import com.backendVn.SWP.dtos.request.CompanyDesignModifyRequest;
import com.backendVn.SWP.dtos.request.DesignCreationRequest;
import com.backendVn.SWP.dtos.request.DesignFeedBackRequest;
import com.backendVn.SWP.dtos.request.DesignUpdateRequest;
import com.backendVn.SWP.dtos.response.ApiResponse;
import com.backendVn.SWP.dtos.response.DesignResponse;
import com.backendVn.SWP.services.DesignService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/design")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DesignController {
    DesignService designService;

    @PostMapping("/{requestOrderId}")
    public ApiResponse<DesignResponse> createDesign(@RequestBody @Valid DesignCreationRequest designCreationRequest, @PathVariable Integer requestOrderId) {
        DesignResponse designResponse = designService.createDesign(designCreationRequest, requestOrderId);
        return ApiResponse.<DesignResponse>builder()
                .result(designResponse)
                .build();
    }

    @PutMapping("/{designId}")
    public ApiResponse<DesignResponse> updateDesign(@PathVariable Integer designId, @RequestBody @Valid DesignUpdateRequest designUpdateRequest) {
        DesignResponse designResponse = designService.updateDesign(designId, designUpdateRequest);
        return ApiResponse.<DesignResponse>builder()
                .result(designResponse)
                .build();
    }


    @GetMapping
    public ApiResponse<List<DesignResponse>> getAllDesigns() {
        List<DesignResponse> designResponse = designService.getAllDesign();
        return ApiResponse.<List<DesignResponse>>builder()
                .result(designResponse)
                .build();
    }

    @GetMapping("/{requestOrderId}")
    public ApiResponse<DesignResponse> getDesignById(@PathVariable Integer requestOrderId) {
        DesignResponse designResponse = designService.getDesignById(requestOrderId);
        return ApiResponse.<DesignResponse>builder()
                .result(designResponse)
                .build();
    }

    @PutMapping("/denyDesign/{designId}")
    public ApiResponse<DesignResponse> denyDesign(@PathVariable Integer designId, @RequestBody @Valid DesignFeedBackRequest request) {
        return ApiResponse.<DesignResponse>builder()
                .result(designService.denyDesign(designId, request))
                .build();
    }

    @GetMapping("/getAllCompanyDesign1")
    public ApiResponse<List<DesignResponse>> getAllCompanyDesign() {
        return ApiResponse.<List<DesignResponse>>builder()
                .result(designService.getAllCompanyDesign1())
                .build();
    }

    @GetMapping("/getAllCompanyDesign2")
    public ApiResponse<List<DesignResponse>> getAllCompanyDesign(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer mainStone,
            @RequestParam(required = false) Integer subStone) {
        return ApiResponse.<List<DesignResponse>>builder()
                .result(designService.getAllCompanyDesign2(search, category, mainStone, subStone))
                .build();
    }

    @GetMapping("/getAllCompanyDesign")
    public ApiResponse<List<DesignResponse>> getAllCompanyDesign(@RequestParam(required = false) String designName,
                                                                 @RequestParam(required = false) String category) {
        return ApiResponse.<List<DesignResponse>>builder()
                .result(designService.getAllCompanyDesign(designName, category))
                .build();
    }


    @PostMapping("/createCompanyDesign")
    public ApiResponse<DesignResponse> createCompanyDesign(@RequestBody @Valid CompanyDesignModifyRequest request){
        return ApiResponse.<DesignResponse>builder()
                .result(designService.modifyDesign(request))
                .build();
    }

    @PutMapping("/updateCompanyDesign/{designId}")
    public ApiResponse<DesignResponse> updateCompanyDesign(@PathVariable Integer designId, @RequestBody @Valid CompanyDesignModifyRequest request){
        return ApiResponse.<DesignResponse>builder()
                .result(designService.updateCompanyDesign(designId,request))
                .build();
    }

    @DeleteMapping("/deleteDesign/{designId}")
    public ApiResponse<DesignResponse> deleteDesign(@PathVariable Integer designId) {
        return ApiResponse.<DesignResponse>builder()
                .result(designService.deleteDesign(designId))
                .build();
    }
}
