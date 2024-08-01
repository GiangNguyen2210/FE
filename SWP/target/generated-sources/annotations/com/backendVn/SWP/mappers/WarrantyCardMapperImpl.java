package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.response.WarrantyCardResponse;
import com.backendVn.SWP.entities.WarrantyCard;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class WarrantyCardMapperImpl implements WarrantyCardMapper {

    @Override
    public WarrantyCardResponse toWarrantyCardResponse(WarrantyCard warrantyCard) {
        if ( warrantyCard == null ) {
            return null;
        }

        WarrantyCardResponse.WarrantyCardResponseBuilder warrantyCardResponse = WarrantyCardResponse.builder();

        warrantyCardResponse.createdAt( warrantyCard.getCreatedAt() );
        warrantyCardResponse.endAt( warrantyCard.getEndAt() );

        return warrantyCardResponse.build();
    }
}
