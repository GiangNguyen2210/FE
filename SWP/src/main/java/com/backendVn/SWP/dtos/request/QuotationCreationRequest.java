package com.backendVn.SWP.dtos.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuotationCreationRequest {
    @NotNull(message = "COST_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = false, message = "COST_INVALID")
    BigDecimal cost;

    @NotNull(message = "COST_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = false, message = "COST_INVALID")
    BigDecimal materialPrice;

    @NotNull(message = "WEIGHT_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = false, message = "WEIGHT_INVALID")
    BigDecimal materialWeight;

    BigDecimal stonePrice;

    @NotNull(message = "COST_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = false, message = "COST_INVALID")
    BigDecimal producePrice;
}
