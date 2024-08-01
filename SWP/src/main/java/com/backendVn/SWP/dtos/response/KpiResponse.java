package com.backendVn.SWP.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KpiResponse {
    private String staffName;
    private String staffTitle;
    private Long orderCount;
}
