package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.CompanyDesignModifyRequest;
import com.backendVn.SWP.dtos.request.DesignCreationRequest;
import com.backendVn.SWP.dtos.request.DesignUpdateRequest;
import com.backendVn.SWP.dtos.response.DesignResponse;
import com.backendVn.SWP.entities.Design;
import com.backendVn.SWP.entities.Material;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class DesignMapperImpl implements DesignMapper {

    @Override
    public Design toDesign(DesignCreationRequest designCreationRequest) {
        if ( designCreationRequest == null ) {
            return null;
        }

        Design.DesignBuilder design = Design.builder();

        design.description( designCreationRequest.getDescription() );

        return design.build();
    }

    @Override
    public void updateDesign(Design design, DesignUpdateRequest designUpdateRequest) {
        if ( designUpdateRequest == null ) {
            return;
        }

        design.setDescription( designUpdateRequest.getDescription() );
    }

    @Override
    public DesignResponse toDesignResponse(Design design, List<String> listURLImage) {
        if ( design == null && listURLImage == null ) {
            return null;
        }

        DesignResponse.DesignResponseBuilder designResponse = DesignResponse.builder();

        if ( design != null ) {
            designResponse.mainStoneId( designMainStoneId( design ) );
            designResponse.subStoneId( designSubStoneId( design ) );
            designResponse.id( design.getId() );
            designResponse.designName( design.getDesignName() );
            designResponse.description( design.getDescription() );
            designResponse.category( design.getCategory() );
            designResponse.materialWeight( design.getMaterialWeight() );
            designResponse.materialName( design.getMaterialName() );
        }
        List<String> list = listURLImage;
        if ( list != null ) {
            designResponse.listURLImage( new ArrayList<String>( list ) );
        }

        return designResponse.build();
    }

    @Override
    public Design modifyCompanyDesign(CompanyDesignModifyRequest request) {
        if ( request == null ) {
            return null;
        }

        Design.DesignBuilder design = Design.builder();

        design.designName( request.getDesignName() );
        design.description( request.getDescription() );
        design.category( request.getCategory() );
        design.materialWeight( request.getMaterialWeight() );
        design.materialName( request.getMaterialName() );

        return design.build();
    }

    @Override
    public void updateCompanyDesign(Design design, CompanyDesignModifyRequest request) {
        if ( request == null ) {
            return;
        }

        design.setDesignName( request.getDesignName() );
        design.setDescription( request.getDescription() );
        design.setCategory( request.getCategory() );
        design.setMaterialWeight( request.getMaterialWeight() );
        design.setMaterialName( request.getMaterialName() );
    }

    private Integer designMainStoneId(Design design) {
        if ( design == null ) {
            return null;
        }
        Material mainStone = design.getMainStone();
        if ( mainStone == null ) {
            return null;
        }
        Integer id = mainStone.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer designSubStoneId(Design design) {
        if ( design == null ) {
            return null;
        }
        Material subStone = design.getSubStone();
        if ( subStone == null ) {
            return null;
        }
        Integer id = subStone.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
