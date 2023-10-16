package msm.service.expense.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponseDto {
    String responseCode;
    String responseDesc;
    Object payload;

}
