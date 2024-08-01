package com.backendVn.SWP.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DesignCreationRequest {

    @NotEmpty(message = "DESCRIPTION_EMPTY")
    String description;

    @NotEmpty(message = "DESIGN_URL_IS_REQUIRED")
    List<String> listURLImage;
}
