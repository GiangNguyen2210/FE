package com.backendVn.SWP.repositories;

import com.backendVn.SWP.entities.Invoice;
import com.backendVn.SWP.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByCreatedAtBetween(Instant start, Instant end);

    Optional<Invoice> findByRequestID(Request request);

    Long countByCreatedAtBetween(Instant startDate, Instant endDate);
}