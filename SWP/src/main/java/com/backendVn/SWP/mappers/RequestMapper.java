package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.RequestCreationRequestForCustomerDesign;
import com.backendVn.SWP.dtos.response.RequestResponse;
import com.backendVn.SWP.dtos.response.TransactionResponse;
import com.backendVn.SWP.entities.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "status", constant = "Unapproved")
    Request toRequest(RequestCreationRequestForCustomerDesign requestCreationRequestForCustomerDesign);

    void updateRequestFromDto(@MappingTarget Request request, RequestCreationRequestForCustomerDesign requestCreationRequestForCustomerDesign);

    @Mapping(target = "customerID", source = "customerID.id")
    @Mapping(target = "saleStaffID", source = "saleStaffid.id")
    @Mapping(target = "mainStone", source = "mainStone.materialName")
    @Mapping(target = "subStone", source = "subStone.materialName")
    @Mapping(target = "materialName", source = "materialID.materialName")
    @Mapping(target = "companyDesign", source = "companyDesign.id")
    @Mapping(target = "sellCost", source = "materialID.pricePerUnit")
    @Mapping(target = "updated", source = "materialID.updateTime")
    RequestResponse toRequestResponse(Request request);

    TransactionResponse toTransactionResponse(Request request);

    @Mapping(target = "customerID", source = "request.customerID.id")
    @Mapping(target = "saleStaffID", source = "request.saleStaffid.id")
    @Mapping(target = "mainStone", source = "request.mainStone.materialName")
    @Mapping(target = "subStone", source = "request.subStone.materialName")
    @Mapping(target = "materialName", source = "request.materialID.materialName")
    @Mapping(target = "companyDesign", source = "request.companyDesign.id")
    @Mapping(target = "sellCost", source = "request.materialID.pricePerUnit")
    @Mapping(target = "updated", source = "request.materialID.updateTime")
    RequestResponse toRequestResponseWithCustomerDesign(Request request, List<String> listURLImage);
}
