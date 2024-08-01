package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.MaterialRequest;
import com.backendVn.SWP.dtos.response.MaterialResponse;
import com.backendVn.SWP.entities.Material;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MaterialMapper {
    Material toMaterial(MaterialRequest material);
    MaterialResponse toMaterialResponse(Material material);
    void updateMaterial(@MappingTarget Material material, MaterialRequest materialRequest);
}
