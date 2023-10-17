package com.msm.service.expense.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponseDto {
    String responseCode;
    String responseDesc;
    Object payload;

}
