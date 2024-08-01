package com.backendVn.SWP.services;

import com.backendVn.SWP.dtos.request.MaterialRequest;
import com.backendVn.SWP.dtos.response.MaterialResponse;
import com.backendVn.SWP.entities.Material;
import com.backendVn.SWP.exception.AppException;
import com.backendVn.SWP.exception.ErrorCode;
import com.backendVn.SWP.mappers.MaterialMapper;
import com.backendVn.SWP.repositories.MaterialRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MaterialService {
    MaterialMapper materialMapper;
    MaterialRepository materialRepository;

    @PreAuthorize("hasAuthority('SCOPE_PRODUCTION_STAFF')")
    public MaterialResponse createMaterial(MaterialRequest material) {
        if(material.getType().equalsIgnoreCase("gold")){
            throw new AppException(ErrorCode.MATERIAL_TYPE_INVALID);
        }

        Material material1 = materialMapper.toMaterial(material);

        material1.setUpdateTime(Instant.now());

        Material savedMaterial = materialRepository.save(material1);
        return materialMapper.toMaterialResponse(savedMaterial);
    }

    @PreAuthorize("hasAuthority('SCOPE_PRODUCTION_STAFF')")
    public MaterialResponse updateMaterial(MaterialRequest materialRequest,Integer id) {
        Material material = materialRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MATERIAL_NOT_FOUND));
        materialMapper.updateMaterial(material, materialRequest);
        return materialMapper.toMaterialResponse(materialRepository.save(material));
    }

    public List<MaterialResponse> getMaterialNotGold() {
        return materialRepository.findAllByTypeIsNotGold("Gold").orElseThrow(() -> new AppException(ErrorCode.NO_MATERIAL_IN_THE_LIST))
                .stream().map(materialMapper::toMaterialResponse).toList();
    }

    public void deleteMaterial(Integer id) {
        materialRepository.deleteById(id);
    }
}
