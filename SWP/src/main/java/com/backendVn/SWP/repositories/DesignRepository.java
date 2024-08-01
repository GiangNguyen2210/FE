package com.backendVn.SWP.repositories;

import com.backendVn.SWP.entities.Design;
import com.backendVn.SWP.entities.Material;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DesignRepository extends JpaRepository<Design, Integer> {
    Optional<List<Design>> findByDesignNameIsNotLike(String designName);

    @Query("SELECT d FROM Design d WHERE d.designName NOT LIKE 'Customer''s design' AND " +
            "(:search IS NULL OR d.designName LIKE %:search%) AND " +
            "(:category IS NULL OR d.category = :category) AND " +
            "(:mainStone IS NULL OR d.mainStone = :mainStone) AND " +
            "(:subStone IS NULL OR d.subStone = :subStone)")
    List<Design> findAllWithFilters(@Param("search") String search,
                                    @Param("category") String category,
                                    @Param("mainStone") Material mainStone,
                                    @Param("subStone") Material subStone);

    @Query("SELECT d FROM Design d WHERE (:designName IS NULL OR LOWER(d.designName) LIKE LOWER(CONCAT('%', :designName, '%'))) AND (:category IS NULL OR :category = '' OR d.category = :category)")
    List<Design> searchAndFilterDesigns(@Param("designName") String designName, @Param("category") String category);
}