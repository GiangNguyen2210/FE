package com.backendVn.SWP.dtos.response;

import com.backendVn.SWP.entities.Request;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    Integer id;

    Integer requestID;

    BigDecimal amount;

    Instant paymentDate;

    Instant createdAt;

    String paymentType;

    String status;
}
