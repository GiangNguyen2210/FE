package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.InvoiceDetailUpdateRequest;
import com.backendVn.SWP.dtos.response.InvoiceDetailResponse;
import com.backendVn.SWP.entities.InvoiceDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceDetailMapper {

    void updateInvoiceDetail(@MappingTarget InvoiceDetail invoiceDetail, InvoiceDetailUpdateRequest invoiceDetailUpdateRequest);

    @Mapping(target = "invoiceID", source = "invoiceID.id")
    @Mapping(target = "materialID", source = "materialID.id")
    InvoiceDetailResponse toInvoiceDetailResponse(InvoiceDetail invoiceDetail);
}
