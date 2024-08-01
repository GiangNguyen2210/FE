package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.ProcessUpdateRequest;
import com.backendVn.SWP.dtos.response.ProcessResponse;
import com.backendVn.SWP.entities.Process;
import com.backendVn.SWP.entities.RequestOrder;
import com.backendVn.SWP.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ProcessMapperImpl implements ProcessMapper {

    @Override
    public ProcessResponse toProcessResponse(Process process) {
        if ( process == null ) {
            return null;
        }

        ProcessResponse.ProcessResponseBuilder processResponse = ProcessResponse.builder();

        processResponse.requestOrderId( processRequestOrderidId( process ) );
        processResponse.updatedBy( processUpdatedById( process ) );
        processResponse.id( process.getId() );
        processResponse.updatedAt( process.getUpdatedAt() );
        processResponse.status( process.getStatus() );

        return processResponse.build();
    }

    @Override
    public void updateProcess(Process process, ProcessUpdateRequest processUpdateRequest) {
        if ( processUpdateRequest == null ) {
            return;
        }

        process.setStatus( processUpdateRequest.getStatus() );
    }

    private Integer processRequestOrderidId(Process process) {
        if ( process == null ) {
            return null;
        }
        RequestOrder requestOrderid = process.getRequestOrderid();
        if ( requestOrderid == null ) {
            return null;
        }
        Integer id = requestOrderid.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer processUpdatedById(Process process) {
        if ( process == null ) {
            return null;
        }
        User updatedBy = process.getUpdatedBy();
        if ( updatedBy == null ) {
            return null;
        }
        Integer id = updatedBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
