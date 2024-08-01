package com.backendVn.SWP.dtos.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyDesignModifyRequest {

    @NotEmpty(message = "DESIGN_NAME_EMPTY")
    @Size(max = 100, message = "NAME_SIZE_INVALID")
    String designName;

    @NotEmpty(message = "DESCRIPTION_EMPTY")
    String description;

    @NotEmpty(message = "DESIGN_URL_IS_REQUIRED")
    List<String> listURLImage;

    @Pattern(regexp = "RING|NECKLACE|BRACELET|EARRINGS", message = "INVALID_CATEGORY")
    String category;

    @NotNull(message = "WEiGHT_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = false, message = "WEIGHT_INVALID")
    BigDecimal materialWeight;

    Integer mainStoneId;

    Integer subStoneId;

    @NotEmpty(message = "MATERIAL_NAME_EMPTY")
    @Size(max = 100, message = "MATERIAL_SIZE_INVALID")
    String materialName;
}
