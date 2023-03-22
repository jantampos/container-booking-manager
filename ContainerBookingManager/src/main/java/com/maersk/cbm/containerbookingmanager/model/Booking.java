package com.maersk.cbm.containerbookingmanager.model;


import com.maersk.cbm.containerbookingmanager.model.enums.ContainerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("bookings")
public class Booking {

    @PrimaryKey
    private String bookingRef;

    private Integer containerSize;

    @CassandraType(type = CassandraType.Name.TEXT)
    private ContainerType containerType;

    @Size(message = "origin must be between 5 to 20 characters", min = 2, max = 20)
    private String origin;

    @Size(message = "destination must be between 5 to 20 characters", min = 2, max = 20)
    private String destination;

    @Min(message = "quantity should not be less than 1", value = 1)
    @Max(message = "quantity should not be greater than 100", value = 100)
    private Integer quantity;

    @Pattern(message = "must match ISO-8601 date and time for UTC timezone fromat",
             regexp="^(-?(?:[1-9][0-9]*)?[0-9]{4})-(1[0-2]|0[1-9])-(3[01]|0[1-9]|[12][0-9])T(2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9])(\\.[0-9]+)?(Z|[+-](?:2[0-3]|[01][0-9]):[0-5][0-9])?$")
    private String timestamp;

}

