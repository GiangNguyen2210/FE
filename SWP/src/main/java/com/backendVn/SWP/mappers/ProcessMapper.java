package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.ProcessUpdateRequest;
import com.backendVn.SWP.dtos.response.ProcessResponse;
import com.backendVn.SWP.entities.Process;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProcessMapper {
    @Mapping(target = "requestOrderId", source = "requestOrderid.id")
    @Mapping(target = "updatedBy", source = "updatedBy.id")
    ProcessResponse toProcessResponse(Process process);

    void updateProcess(@MappingTarget Process process, ProcessUpdateRequest processUpdateRequest);
}
