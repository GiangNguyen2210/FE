package com.backendVn.SWP.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AutoPricingResponse {
    BigDecimal materialPrice;

    BigDecimal materialWeight;

    BigDecimal producePrice;

    BigDecimal stonePrice;
}
