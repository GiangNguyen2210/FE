package com.backendVn.SWP.dtos.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;

import java.io.Serializable;

/**
 * DTO for {@link com.backendVn.SWP.entities.User}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults (level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 3,message = "INVALID_USERNAME")
    String userName;

    @Size(min = 8,message = "INVALID_PASSWORD")
    String password;

    @Email(message = "INVALID_EMAIL")
    String email;

    String address;

    @Pattern(regexp = "ADMIN|PRODUCTION_STAFF|DESIGN_STAFF|SALE_STAFF|CUSTOMER|MANAGER", message = "INVALID_TITLE")
    String title;

    String cusName;

    String phoneNum;
}
