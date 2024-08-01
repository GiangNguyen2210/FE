package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.response.WarrantyCardResponse;
import com.backendVn.SWP.entities.WarrantyCard;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WarrantyCardMapper {
    WarrantyCardResponse toWarrantyCardResponse(WarrantyCard warrantyCard);
}
