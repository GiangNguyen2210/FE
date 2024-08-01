package com.backendVn.SWP.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestOrderResponse {
    Integer id;
    Integer requestID;
    Integer designID;
    Integer designStaff;
    Integer productionStaff;
    String status;
    Instant createdAt;
    String description;
}
