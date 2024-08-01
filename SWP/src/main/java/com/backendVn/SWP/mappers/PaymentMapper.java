package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.response.PaymentResponse;
import com.backendVn.SWP.dtos.response.TransactionResponse;
import com.backendVn.SWP.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "requestID", source = "requestID.id")
    PaymentResponse toPaymentResponse(Payment payment);

    void updateTransactionResponseFromPayment(Payment payment, @MappingTarget TransactionResponse transactionResponse);
}
