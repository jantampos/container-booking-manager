package com.maersk.cbm.containerbookingmanager.model.dtos;

import com.maersk.cbm.containerbookingmanager.model.enums.ContainerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityRequest {

    private Integer containerSize;

    private ContainerType containerType;

    @Size(message = "origin must be between 5 to 20 characters", min = 2, max = 20)
    private String origin;

    @Size(message = "destination must be between 5 to 20 characters", min = 2, max = 20)
    private String destination;

    @Min(value = 1, message = "quantity should not be less than 1")
    @Max(value = 100, message = "quantity should not be greater than 100")
    private Integer quantity;
}
