package com.backendVn.SWP.repositories;

import com.backendVn.SWP.entities.Design;
import com.backendVn.SWP.entities.Request;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.time.Instant;

public interface RequestOrderRepository extends JpaRepository<RequestOrder, Integer> {
    Optional<RequestOrder> findByRequestID(Request request);

    List<RequestOrder> findAllByDesignStaffAndStatusIsNotLike(User designStaff, String status);

    Optional<RequestOrder> findByDesignID(Design design);

    Optional<List<RequestOrder>> findByStatus(String status);

    List<RequestOrder> findAllByProductionStaffAndStatusIsLike(User designStaff,String status);

    List<RequestOrder> findByCreatedAtBetween(Instant start, Instant end);
}