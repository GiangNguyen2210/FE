package com.backendVn.SWP.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessResponse {
    Integer id;
    Integer requestOrderId;
    Instant updatedAt;
    Integer updatedBy;
    String status;
}
