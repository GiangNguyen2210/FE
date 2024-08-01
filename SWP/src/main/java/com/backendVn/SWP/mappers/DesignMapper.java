package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.CompanyDesignModifyRequest;
import com.backendVn.SWP.dtos.request.DesignCreationRequest;
import com.backendVn.SWP.dtos.request.DesignUpdateRequest;
import com.backendVn.SWP.dtos.response.DesignResponse;
import com.backendVn.SWP.entities.Design;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DesignMapper {

    Design toDesign(DesignCreationRequest designCreationRequest);
    void updateDesign(@MappingTarget Design design, DesignUpdateRequest designUpdateRequest);

    @Mapping(target = "mainStoneId", source = "design.mainStone.id")
    @Mapping(target = "subStoneId", source = "design.subStone.id")
    DesignResponse toDesignResponse(Design design, List<String> listURLImage);

    Design modifyCompanyDesign(CompanyDesignModifyRequest request);
    void updateCompanyDesign(@MappingTarget Design design, CompanyDesignModifyRequest request);
}
