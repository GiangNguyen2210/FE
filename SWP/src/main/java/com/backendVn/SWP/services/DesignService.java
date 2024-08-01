package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.request.CompanyDesignModifyRequest;
import com.backendVn.SWP.dtos.request.DesignCreationRequest;
import com.backendVn.SWP.dtos.request.DesignFeedBackRequest;
import com.backendVn.SWP.dtos.request.DesignUpdateRequest;
import com.backendVn.SWP.dtos.response.DesignResponse;
import com.backendVn.SWP.entities.Design;
import com.backendVn.SWP.entities.Material;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.mappers.DesignMapper;
import com.backendVn.SWP.repositories.DesignRepository;
import com.backendVn.SWP.repositories.MaterialRepository;
import com.backendVn.SWP.repositories.RequestOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DesignService {
    DesignRepository designRepository;
    DesignMapper designMapper;
    RequestOrderRepository requestOrderRepository;
    MaterialRepository materialRepository;

    public String createCSV(List<String> listUrRLImage){
        if (listUrRLImage.isEmpty())return "";

        String uRLImage = "";

        for(String imageURL : listUrRLImage) {
            if (uRLImage.isEmpty()){
                uRLImage = imageURL;
            } else {
                uRLImage += "," + imageURL;
            }
        }

        return uRLImage;
    }

    public List<String> brokeCSV(String uRLImage){
        if (uRLImage == null || uRLImage.isEmpty()){
            return new ArrayList<>();
        }
        return Arrays.stream(uRLImage.split(",")).toList();
    }

    @PreAuthorize("hasAuthority('SCOPE_DESIGN_STAFF')")
    public DesignResponse createDesign(DesignCreationRequest designCreationRequest, Integer requestOrderId){
        if (designCreationRequest.getListURLImage() == null || designCreationRequest.getListURLImage().isEmpty()){
            throw new AppException(ErrorCode.NO_URLIMAGE_IN_DESIGN_REQUEST);
        }

        RequestOrder requestOrder = requestOrderRepository.findById(requestOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        requestOrder.setStatus("Waiting for customer's decision");

        Design design = designMapper.toDesign(designCreationRequest);
        design.setURLImage(createCSV(designCreationRequest.getListURLImage()));
        design.setDesignName("Customer's design");
        design.setCategory(requestOrder.getRequestID().getCategory());
        design.setCategory(requestOrder.getRequestID().getCategory());
        design.setMaterialName(requestOrder.getRequestID().getMaterialID().getMaterialName());
        design.setMaterialWeight(requestOrder.getRequestID().getMaterialWeight());
        design.setMainStone(requestOrder.getRequestID().getMainStone());
        design.setSubStone(requestOrder.getRequestID().getSubStone());

        Design savedDesign = designRepository.save(design);

        DesignResponse designResponse = designMapper.toDesignResponse(savedDesign, brokeCSV(design.getURLImage()));

        requestOrder.setDesignID(design);
        requestOrderRepository.save(requestOrder);

        return designResponse;
    }

    public List<DesignResponse> getAllDesign() {
        List<Design> designs = designRepository.findAll();

        List<DesignResponse> designResponses = new ArrayList<>();

        for (Design design : designs) {
            designResponses.add(designMapper.toDesignResponse(design, brokeCSV(design.getURLImage())));
        }

        return designResponses;
    }

    @PreAuthorize("hasAuthority('SCOPE_DESIGN_STAFF')")
    public DesignResponse updateDesign(Integer designId, DesignUpdateRequest designUpdateRequest) {
        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));

        RequestOrder requestOrder = requestOrderRepository.findByDesignID(design)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        requestOrder.setStatus("Waiting for customer's decision");

        design.setURLImage(createCSV(designUpdateRequest.getListURLImage()));
        designMapper.updateDesign(design, designUpdateRequest);

        requestOrderRepository.save(requestOrder);
        return designMapper.toDesignResponse(designRepository.save(design), brokeCSV(design.getURLImage()));
    }

//    @PreAuthorize("hasAnyAuthority('SCOPE_CUSTOMER', 'SCOPE_DEISGN_STAFF', 'SCOPE_PRODUCTION_STAFF')")
    public DesignResponse getDesignById(Integer requestOrderId) {
        RequestOrder requestOrder = requestOrderRepository.findById(requestOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        if(requestOrder.getDesignID() == null){
            throw new AppException(ErrorCode.NO_DESIGN_WAS_ASSIGNED);
        }

        Design design = designRepository.findById(requestOrder.getDesignID().getId())
                .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));

        return designMapper.toDesignResponse(design, brokeCSV(design.getURLImage()));
    }

    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')")
    public DesignResponse denyDesign(Integer designId, DesignFeedBackRequest request){
        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));

        RequestOrder requestOrder = requestOrderRepository.findByDesignID(design)
                .orElseThrow(() -> new AppException(ErrorCode.REQUEST_ORDER_NOT_FOUND));

        requestOrder.setStatus("Design Denied");

        requestOrder.setDescription(request.getDescription());

        requestOrderRepository.save(requestOrder);

        return designMapper.toDesignResponse(design, brokeCSV(design.getURLImage()));
    }

    @PreAuthorize("hasAuthority('SCOPE_DESIGN_STAFF')")
    public DesignResponse modifyDesign(CompanyDesignModifyRequest request){
        if(request.getListURLImage() == null || request.getListURLImage().isEmpty()){
            throw new AppException(ErrorCode.NO_URLIMAGE_IN_DESIGN_REQUEST);
        }

        Design design = designMapper.modifyCompanyDesign(request);

        design.setURLImage(createCSV(request.getListURLImage()));

        design.setMainStone(getMaterialById(request.getMainStoneId()));
        design.setSubStone(getMaterialById(request.getSubStoneId()));

        return designMapper.toDesignResponse(designRepository.save(design), brokeCSV(design.getURLImage()));
    }

    private Material getMaterialById(Integer materialId) {
        return materialId == 0 ? null : materialRepository.findById(materialId)
                .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));
    }

    @PreAuthorize("hasAuthority('SCOPE_DESIGN_STAFF')")
    public DesignResponse updateCompanyDesign(Integer designId ,CompanyDesignModifyRequest request){
        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));

        if (request.getListURLImage() == null || request.getListURLImage().isEmpty()){
            throw new AppException(ErrorCode.NO_URLIMAGE_IN_DESIGN_REQUEST);
        }

        designMapper.updateCompanyDesign(design, request);

        design.setURLImage(createCSV(request.getListURLImage()));

        design.setMainStone(getMaterialById(request.getMainStoneId()));
        design.setSubStone(getMaterialById(request.getSubStoneId()));

        return designMapper.toDesignResponse(designRepository.save(design), brokeCSV(design.getURLImage()));
    }

    public List<DesignResponse> getAllCompanyDesign1() {
        List<Design> designs = designRepository.findByDesignNameIsNotLike("Customer's design")
                .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));

        List<DesignResponse> designResponses = new ArrayList<>();

        for (Design design : designs) {
            designResponses.add(designMapper.toDesignResponse(design, brokeCSV(design.getURLImage())));
        }

        return designResponses;
    }

    public List<DesignResponse> getAllCompanyDesign2(String search, String category, Integer mainStoneId, Integer subStoneId) {
        Material mainStone = null;
        Material subStone = null;

        if (mainStoneId != null) {
            mainStone = materialRepository.findById(mainStoneId)
                    .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));
        }

        if (subStoneId != null) {
            subStone = materialRepository.findById(subStoneId)
                    .orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));
        }

        List<Design> designs = designRepository.findAllWithFilters(search, category, mainStone, subStone);

        if (designs.isEmpty()) {
            throw new AppException(ErrorCode.DESIGN_NOT_FOUND);
        }

        List<DesignResponse> designResponses = new ArrayList<>();

        for (Design design : designs) {
            designResponses.add(designMapper.toDesignResponse(design, brokeCSV(design.getURLImage())));
        }

        return designResponses;
    }

    public List<DesignResponse> getAllCompanyDesign(String designName, String category) {
        List<Design> designs = designRepository.searchAndFilterDesigns(designName, category);
        List<DesignResponse> designResponses = new ArrayList<>();
        for (Design design : designs) {
            if (design.getDesignName() != null && design.getDesignName().equals("Customer's design")) continue;
            designResponses.add(designMapper.toDesignResponse(design, brokeCSV(design.getURLImage())));
        }
        return designResponses;
    }

    @PreAuthorize("hasAuthority('SCOPE_DESIGN_STAFF')")
    public DesignResponse deleteDesign(Integer designId){
        Design design = designRepository.findById(designId)
                .orElseThrow(() -> new AppException(ErrorCode.DESIGN_NOT_FOUND));

        design.setDesignName(null);

        return designMapper.toDesignResponse(designRepository.save(design), brokeCSV(design.getURLImage()));
    }
}
