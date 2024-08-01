package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.response.RequestOrderResponse;
import com.backendVn.SWP.entities.RequestOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequestOrderMapper {

    @Mapping(target = "requestID", source = "requestID.id")
    @Mapping(target = "designID", source = "designID.id")
    @Mapping(target = "designStaff", source = "designStaff.id")
    @Mapping(target = "productionStaff", source = "productionStaff.id")
    @Mapping(target = "description", source = "description")
    RequestOrderResponse toRequestOrderResponse(RequestOrder requestOrder);
}
