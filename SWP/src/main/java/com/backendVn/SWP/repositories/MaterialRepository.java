package com.backendVn.SWP.repositories;

import com.backendVn.SWP.entities.Material;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    Optional<Material> findByMaterialNameAndUpdateTime(String materialName, Instant updateTime);

    @Query("SELECT material FROM Material material WHERE material.type != :type ")
    Optional<List<Material>> findAllByTypeIsNotGold(@Param("type") String type);

    Optional<List<Material>> findByMaterialName(String materialName);
}