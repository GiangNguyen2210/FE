package com.backendVn.SWP.repositories;

import com.backendVn.SWP.entities.Invoice;
import com.backendVn.SWP.entities.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {
    Optional<List<InvoiceDetail>> findByInvoiceID(Invoice invoice);
}