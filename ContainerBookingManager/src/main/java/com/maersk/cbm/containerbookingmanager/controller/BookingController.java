package com.maersk.cbm.containerbookingmanager.controller;

import com.maersk.cbm.containerbookingmanager.model.Booking;
import com.maersk.cbm.containerbookingmanager.model.dtos.AvailabilityRequest;
import com.maersk.cbm.containerbookingmanager.model.dtos.AvailabilityResponse;
import com.maersk.cbm.containerbookingmanager.model.dtos.BookingResponse;
import com.maersk.cbm.containerbookingmanager.service.IAvailabilityService;
import com.maersk.cbm.containerbookingmanager.service.IBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
* Controller class taking care of booking related requests
**/
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final IBookingService bookingService;
    private final IAvailabilityService availabilityService;

    public BookingController(IBookingService bookingService, IAvailabilityService availabilityService) {
        this.bookingService = bookingService;
        this.availabilityService = availabilityService;
    }

    @GetMapping("/get-bookings")
    public Flux<Booking> getBookings() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/check-availability")
    public AvailabilityResponse checkAvailability(@Valid @RequestBody AvailabilityRequest availabilityRequest) {
        boolean isAvailable = availabilityService.checkAvailability(availabilityRequest);
        return AvailabilityResponse.builder().available(isAvailable).build();
    }

    @PostMapping("/book-container")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookingResponse> bookContainer(@Valid @RequestBody Booking booking) {
        return bookingService.createBooking(booking)
                .map(savedBooking -> BookingResponse.builder()
                        .bookingRef(savedBooking.getBookingRef())
                        .build());
    }
}
