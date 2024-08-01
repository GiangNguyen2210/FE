package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.response.RequestOrderResponse;
import com.backendVn.SWP.entities.Design;
import com.backendVn.SWP.entities.Request;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class RequestOrderMapperImpl implements RequestOrderMapper {

    @Override
    public RequestOrderResponse toRequestOrderResponse(RequestOrder requestOrder) {
        if ( requestOrder == null ) {
            return null;
        }

        RequestOrderResponse.RequestOrderResponseBuilder requestOrderResponse = RequestOrderResponse.builder();

        requestOrderResponse.requestID( requestOrderRequestIDId( requestOrder ) );
        requestOrderResponse.designID( requestOrderDesignIDId( requestOrder ) );
        requestOrderResponse.designStaff( requestOrderDesignStaffId( requestOrder ) );
        requestOrderResponse.productionStaff( requestOrderProductionStaffId( requestOrder ) );
        requestOrderResponse.description( requestOrder.getDescription() );
        requestOrderResponse.id( requestOrder.getId() );
        requestOrderResponse.status( requestOrder.getStatus() );
        requestOrderResponse.createdAt( requestOrder.getCreatedAt() );

        return requestOrderResponse.build();
    }

    private Integer requestOrderRequestIDId(RequestOrder requestOrder) {
        if ( requestOrder == null ) {
            return null;
        }
        Request requestID = requestOrder.getRequestID();
        if ( requestID == null ) {
            return null;
        }
        Integer id = requestID.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer requestOrderDesignIDId(RequestOrder requestOrder) {
        if ( requestOrder == null ) {
            return null;
        }
        Design designID = requestOrder.getDesignID();
        if ( designID == null ) {
            return null;
        }
        Integer id = designID.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer requestOrderDesignStaffId(RequestOrder requestOrder) {
        if ( requestOrder == null ) {
            return null;
        }
        User designStaff = requestOrder.getDesignStaff();
        if ( designStaff == null ) {
            return null;
        }
        Integer id = designStaff.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer requestOrderProductionStaffId(RequestOrder requestOrder) {
        if ( requestOrder == null ) {
            return null;
        }
        User productionStaff = requestOrder.getProductionStaff();
        if ( productionStaff == null ) {
            return null;
        }
        Integer id = productionStaff.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
