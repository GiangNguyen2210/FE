package com.backendVn.SWP.repositories;

import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.entities.WarrantyCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarrantyCardRepository extends JpaRepository<WarrantyCard, Integer> {


    Optional<WarrantyCard> findByRequestOrder(RequestOrder requestOrder);
}