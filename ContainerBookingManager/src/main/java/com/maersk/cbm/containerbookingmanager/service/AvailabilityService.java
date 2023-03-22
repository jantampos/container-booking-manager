package com.maersk.cbm.containerbookingmanager.service;

import com.maersk.cbm.containerbookingmanager.model.Availability;
import com.maersk.cbm.containerbookingmanager.model.dtos.AvailabilityRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * Service responsible for calling the external availability service and returning the availability status
 **/
@Service
public class AvailabilityService implements IAvailabilityService {

    public static final String AVAILABILITY_API_ENDPOINT = "https://maersk.com/api/bookings/checkAvailable";

    private final RestTemplate restTemplate;

    public AvailabilityService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean checkAvailability(AvailabilityRequest availabilityRequest) {
        Availability availability = tryConnectToAvailabilityService();
        return availability.getAvailableSpace() > 0;
    }


    private Availability tryConnectToAvailabilityService() {
        try {
            ResponseEntity<Availability> responseEntity = restTemplate
                    .getForEntity(AVAILABILITY_API_ENDPOINT, Availability.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                Availability availability = responseEntity.getBody();
                return availability;
            }
            return getRandomAvailability();
        } catch (Exception exception) {
            // this path is added to simulate a successful call to the availability service in case of an exception
            // as the endpoint connection is currently failing does not exist
            return getRandomAvailability();
        }
    }

    private Availability getRandomAvailability() {
        Random random = new Random();
        return Availability.builder()
                .availableSpace(random.ints(-10, 10).findFirst().getAsInt())
                .build();
    }
}
