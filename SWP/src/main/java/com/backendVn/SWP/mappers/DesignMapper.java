package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.DesignCreationRequest;
import com.backendVn.SWP.dtos.request.DesignUpdateRequest;
import com.backendVn.SWP.dtos.response.DesignResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DesignMapper {

    Design toDesign(DesignCreationRequest designCreationRequest);
    void updateDesign(@MappingTarget Design design, DesignUpdateRequest designUpdateRequest);
    DesignResponse toDesignResponse(Design request);
}
