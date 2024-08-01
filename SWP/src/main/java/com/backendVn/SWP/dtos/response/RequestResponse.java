package com.backendVn.SWP.dtos.response;

import com.backendVn.SWP.entities.Design;
import com.backendVn.SWP.entities.Material;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestResponse {
    Integer id;
    String description;
    Integer customerID;
    Integer saleStaffID;
    String status;
    Instant createdAt;
    Instant recievedAt;
    Instant endAt;
    String materialName;
    BigDecimal materialWeight;
    String mainStone;
    String subStone;
    String category;
    BigDecimal produceCost;
    Integer companyDesign;
    List<String> listURLImage;
    String deniedReason;
    Double sellCost;
    String updated;
}
