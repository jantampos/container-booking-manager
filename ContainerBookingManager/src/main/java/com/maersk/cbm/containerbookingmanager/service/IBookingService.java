package com.maersk.cbm.containerbookingmanager.service;

import com.maersk.cbm.containerbookingmanager.model.Booking;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBookingService {

    Mono<Booking> createBooking(Booking booking);

    Flux<Booking> getAllBookings();
}
