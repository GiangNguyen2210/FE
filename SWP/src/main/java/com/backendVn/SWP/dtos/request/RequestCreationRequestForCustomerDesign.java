package com.backendVn.SWP.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestCreationRequestForCustomerDesign {
    @NotEmpty(message = "DESCRIPTION_IS_EMPTY")
    String description;

    Double buyCost;

    Double sellCost;

    String updated;

    String goldType;

    @NotNull(message = "WEIGHT_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = false, message = "WEIGHT_INVALID")
    BigDecimal materialWeight;

    Integer mainStoneId;

    Integer subStoneId;

    @Pattern(regexp = "RING|NECKLACE|BRACELET|EARRINGS", message = "INVALID_CATEGORY")
    String category;

    List<String> listURLImage;


}
