package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.QuotationCreationRequest;
import com.backendVn.SWP.dtos.response.AutoPricingResponse;
import com.backendVn.SWP.dtos.response.QuotationResponse;
import com.backendVn.SWP.entities.Quotation;
import com.backendVn.SWP.entities.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuotationMapper {

    @Mapping(target = "cost", source = "cost")
    Quotation toQuotation(QuotationCreationRequest quotationCreationRequest);

    @Mapping(target = "requestID", source = "requestID.id")
    @Mapping(target = "approvedBy", source = "approveBy.id")
    @Mapping(target = "deniedReason", source = "deniedReason")
    QuotationResponse toQuotationResponse(Quotation quotation);

    @Mapping(target = "materialPrice", source = "materialID.pricePerUnit")
    @Mapping(target = "materialWeight", source = "materialWeight")
    @Mapping(target = "producePrice", source = "produceCost")
    AutoPricingResponse toAutoPricingResponse(Request request);

}
