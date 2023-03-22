package com.maersk.cbm.containerbookingmanager.service;

import com.maersk.cbm.containerbookingmanager.model.Availability;
import com.maersk.cbm.containerbookingmanager.model.dtos.AvailabilityRequest;
import com.maersk.cbm.containerbookingmanager.model.enums.ContainerType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static com.maersk.cbm.containerbookingmanager.service.AvailabilityService.AVAILABILITY_API_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AvailabilityServiceTest {

    @InjectMocks
    private AvailabilityService availabilityService;

    @Mock
    private RestTemplate restTemplate;

    private AvailabilityRequest availabilityRequest = initialiseTestAvailabilityRequest();

    @Test
    public void shouldReturnTrueWhenAvailabilityIsGreaterThanZero() {
        // Given
        when(restTemplate.getForEntity(AVAILABILITY_API_ENDPOINT, Availability.class)).thenReturn(
                ResponseEntity.ok(new Availability(10)));
        // When
        boolean isAvailable = availabilityService.checkAvailability(availabilityRequest);
        // Then
        assertTrue(isAvailable);
    }

    @Test
    public void shouldReturnFalseWhenAvailabilityIsLessThanZero() {
        // Given
        when(restTemplate.getForEntity(AVAILABILITY_API_ENDPOINT, Availability.class)).thenReturn(
                ResponseEntity.ok(new Availability(0)));
        // When
        boolean isAvailable = availabilityService.checkAvailability(availabilityRequest);
        // Then
        assertFalse(isAvailable);
    }

    private AvailabilityRequest initialiseTestAvailabilityRequest() {
        return AvailabilityRequest.builder()
                .containerSize(40)
                .containerType(ContainerType.REEFER)
                .destination("Singapore")
                .origin("London")
                .quantity(5)
                .build();
    }
}
