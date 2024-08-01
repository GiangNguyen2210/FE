package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.QuotationCreationRequest;
import com.backendVn.SWP.dtos.response.AutoPricingResponse;
import com.backendVn.SWP.dtos.response.QuotationResponse;
import com.backendVn.SWP.entities.Material;
import com.backendVn.SWP.entities.Quotation;
import com.backendVn.SWP.entities.Request;
import com.backendVn.SWP.entities.User;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class QuotationMapperImpl implements QuotationMapper {

    @Override
    public Quotation toQuotation(QuotationCreationRequest quotationCreationRequest) {
        if ( quotationCreationRequest == null ) {
            return null;
        }

        Quotation quotation = new Quotation();

        quotation.setCost( quotationCreationRequest.getCost() );

        return quotation;
    }

    @Override
    public QuotationResponse toQuotationResponse(Quotation quotation) {
        if ( quotation == null ) {
            return null;
        }

        QuotationResponse.QuotationResponseBuilder quotationResponse = QuotationResponse.builder();

        Integer id = quotationRequestIDId( quotation );
        if ( id != null ) {
            quotationResponse.requestID( id );
        }
        Integer id1 = quotationApproveById( quotation );
        if ( id1 != null ) {
            quotationResponse.approvedBy( id1 );
        }
        quotationResponse.deniedReason( quotation.getDeniedReason() );
        if ( quotation.getId() != null ) {
            quotationResponse.id( quotation.getId() );
        }
        if ( quotation.getCreatedAt() != null ) {
            quotationResponse.createdAt( Date.from( quotation.getCreatedAt() ) );
        }
        quotationResponse.approvedAt( quotation.getApprovedAt() );
        quotationResponse.capitalCost( quotation.getCapitalCost() );
        quotationResponse.cost( quotation.getCost() );

        return quotationResponse.build();
    }

    @Override
    public AutoPricingResponse toAutoPricingResponse(Request request) {
        if ( request == null ) {
            return null;
        }

        AutoPricingResponse.AutoPricingResponseBuilder autoPricingResponse = AutoPricingResponse.builder();

        autoPricingResponse.materialPrice( requestMaterialIDPricePerUnit( request ) );
        autoPricingResponse.materialWeight( request.getMaterialWeight() );
        autoPricingResponse.producePrice( request.getProduceCost() );

        return autoPricingResponse.build();
    }

    private Integer quotationRequestIDId(Quotation quotation) {
        if ( quotation == null ) {
            return null;
        }
        Request requestID = quotation.getRequestID();
        if ( requestID == null ) {
            return null;
        }
        Integer id = requestID.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer quotationApproveById(Quotation quotation) {
        if ( quotation == null ) {
            return null;
        }
        User approveBy = quotation.getApproveBy();
        if ( approveBy == null ) {
            return null;
        }
        Integer id = approveBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private BigDecimal requestMaterialIDPricePerUnit(Request request) {
        if ( request == null ) {
            return null;
        }
        Material materialID = request.getMaterialID();
        if ( materialID == null ) {
            return null;
        }
        BigDecimal pricePerUnit = materialID.getPricePerUnit();
        if ( pricePerUnit == null ) {
            return null;
        }
        return pricePerUnit;
    }
}
