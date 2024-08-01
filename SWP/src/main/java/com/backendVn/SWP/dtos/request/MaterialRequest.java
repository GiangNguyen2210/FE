package com.backendVn.SWP.dtos.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.aspectj.bridge.IMessage;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialRequest {
    @NotNull(message = "TYPE_REQUIRED")
    String type;

    @NotNull(message = "COST_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = false, message = "COST_INVALID")
    BigDecimal pricePerUnit;

    @NotNull(message = "MATERIAL_NAME_REQUIRED")
    String materialName;
}
