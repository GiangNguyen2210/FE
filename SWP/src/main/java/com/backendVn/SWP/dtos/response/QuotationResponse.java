package com.backendVn.SWP.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuotationResponse {
    int id;
    int requestID;
    Date createdAt;
    Instant approvedAt;
    int approvedBy;
    BigDecimal capitalCost;
    BigDecimal cost;
    String deniedReason;
}
