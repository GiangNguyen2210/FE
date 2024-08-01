package com.backendVn.SWP.repositories;

import com.backendVn.SWP.entities.Payment;
import com.backendVn.SWP.entities.Request;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findTop10ByOrderByPaymentDateDesc();

    Optional<List<Payment>> findByRequestIDAndPaymentType(Request requestID, String paymentType);

}