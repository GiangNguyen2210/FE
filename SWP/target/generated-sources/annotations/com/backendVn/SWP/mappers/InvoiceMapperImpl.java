package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.InvoiceUpdateRequest;
import com.backendVn.SWP.dtos.response.InvoiceResponse;
import com.backendVn.SWP.entities.Invoice;
import com.backendVn.SWP.entities.Request;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class InvoiceMapperImpl implements InvoiceMapper {

    @Override
    public void updateInvoice(Invoice invoice, InvoiceUpdateRequest invoiceUpdateRequest) {
        if ( invoiceUpdateRequest == null ) {
            return;
        }

        invoice.setTotalCost( invoiceUpdateRequest.getTotalCost() );
    }

    @Override
    public InvoiceResponse toInvoiceResponse(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }

        InvoiceResponse.InvoiceResponseBuilder invoiceResponse = InvoiceResponse.builder();

        invoiceResponse.requestID( invoiceRequestIDId( invoice ) );
        invoiceResponse.id( invoice.getId() );
        invoiceResponse.totalCost( invoice.getTotalCost() );
        invoiceResponse.createdAt( invoice.getCreatedAt() );

        return invoiceResponse.build();
    }

    private Integer invoiceRequestIDId(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }
        Request requestID = invoice.getRequestID();
        if ( requestID == null ) {
            return null;
        }
        Integer id = requestID.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
