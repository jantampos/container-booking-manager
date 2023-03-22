package com.maersk.cbm.containerbookingmanager.controller;

import com.maersk.cbm.containerbookingmanager.model.Booking;
import com.maersk.cbm.containerbookingmanager.model.dtos.AvailabilityRequest;
import com.maersk.cbm.containerbookingmanager.model.dtos.AvailabilityResponse;
import com.maersk.cbm.containerbookingmanager.model.dtos.BookingResponse;
import com.maersk.cbm.containerbookingmanager.model.enums.ContainerType;
import com.maersk.cbm.containerbookingmanager.service.IAvailabilityService;
import com.maersk.cbm.containerbookingmanager.service.IBookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = BookingController.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
public class BookingControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private IBookingService bookingService;

    @MockBean
    private IAvailabilityService availabilityService;

    @Test
    public void shouldCreateBooking() {
        // Given
        Booking booking = Booking.builder()
                .bookingRef("957000001")
                .containerType(ContainerType.DRY)
                .containerSize(20)
                .origin("Southampton")
                .destination("Singapore")
                .quantity(5)
                .timestamp("2020-10-12T13:53:09Z")
                .build();
        // When
        when(bookingService.createBooking(any(Booking.class))).thenReturn(Mono.just(booking));
        // Then
        webTestClient
                .post().uri("/api/bookings/book-container")
                .bodyValue(booking)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(BookingResponse.class);
    }

    @Test
    public void shouldCheckAvailability() {
        // Given
        AvailabilityRequest request = AvailabilityRequest.builder()
                .containerSize(40)
                .containerType(ContainerType.REEFER)
                .destination("Singapore")
                .origin("London")
                .quantity(5)
                .build();
        when(availabilityService.checkAvailability(any(AvailabilityRequest.class))).thenReturn(true);
        // Then
        webTestClient
                .post().uri("/api/bookings/check-availability")
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AvailabilityResponse.class);
    }
}
