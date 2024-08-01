package com.backendVn.SWP.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceInfor {
    Integer orderId;
    String materialName;
    BigDecimal materialTotalCost;
    BigDecimal produceCost;
    String mainStone;
    String subStone;
    BigDecimal mainStoneCost;
    BigDecimal subStoneCost;
    Instant invoiceCreatedAt;
    BigDecimal invoiceTotalCost;
}
