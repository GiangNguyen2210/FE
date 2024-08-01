package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.InvoiceUpdateRequest;
import com.backendVn.SWP.dtos.response.InvoiceResponse;
import com.backendVn.SWP.entities.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    void updateInvoice(@MappingTarget Invoice invoice, InvoiceUpdateRequest invoiceUpdateRequest);

    @Mapping(target = "requestID", source = "requestID.id")
    InvoiceResponse toInvoiceResponse(Invoice invoice);
}
