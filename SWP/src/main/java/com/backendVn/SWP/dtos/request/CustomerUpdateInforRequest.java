package com.backendVn.SWP.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.message.Message;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerUpdateInforRequest {

    @NotNull(message = "FIELD_NOT_NULL")
    String cusName;

    @NotNull(message = "FIELD_NOT_NULL")
    String phoneNum;

    @NotNull(message = "FIELD_NOT_NULL")
    String address;

    @NotNull(message = "FIELD_NOT_NULL")
    @Email(message = "INVALID_EMAIL")
    String email;
}
