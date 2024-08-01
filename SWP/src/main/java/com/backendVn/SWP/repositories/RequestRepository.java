package com.backendVn.SWP.repositories;

import com.backendVn.SWP.entities.Design;
import com.backendVn.SWP.entities.Request;
import com.backendVn.SWP.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findByStatus(String pendingQuotation);

    List<Request> findAllBySaleStaffidAndStatusIsNot(User user,String status);

    List<Request> findAllBySaleStaffidIsNullAndStatusIs(String status);

    List<Request> findAllByCustomerIDAndStatusIsNotLike(User customer, String status);

    List<Request> findByCreatedAtBetween(Instant startDate, Instant endDate);

    List<Request> findAllByCompanyDesignIsNotNull();

    Optional<Request> findFirstByCompanyDesignAndStatus(Design design, String finished);

    Long countByCreatedAtBetween(Instant startDate, Instant endDate);

    List<Request> findByCreatedAtBetweenAndStatus(Instant startDate, Instant endDate, String finished);

    boolean existsByCompanyDesignAndStatus(Design design, String finished);

    List<Request> findAllByCompanyDesignIsNotNullAndStatus(String finished);
}