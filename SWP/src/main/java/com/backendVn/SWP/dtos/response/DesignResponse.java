package com.backendVn.SWP.dtos.response;

import com.backendVn.SWP.entities.Material;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DesignResponse {
    Integer id;
    String designName;
    String description;
    List<String> listURLImage;
    String category;
    Integer mainStoneId;
    Integer subStoneId;
    BigDecimal materialWeight;
    String materialName;
}
