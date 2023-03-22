package com.maersk.cbm.containerbookingmanager.service;

import com.maersk.cbm.containerbookingmanager.model.Booking;
import com.maersk.cbm.containerbookingmanager.model.dtos.AvailabilityRequest;
import com.maersk.cbm.containerbookingmanager.repository.BookingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Service responsible for persisting the booking request to the database
 **/
@Service
public class BookingService implements IBookingService {

    private static Long REF_START = 957000000L;

    private final BookingRepository bookingRepository;
    private final IAvailabilityService availabilityService;

    public BookingService(BookingRepository bookingRepository, IAvailabilityService availabilityService) {
        this.bookingRepository = bookingRepository;
        this.availabilityService = availabilityService;
    }

    @Override
    public Flux<Booking> getAllBookings() {
        return  bookingRepository.findAll();
    }

    /**
     *  If availability check is needed; call the availability service before proceeding with the saving of the request
     *  This check is omitted for simplicity.
     *
     *  if (checkAvailability(bookingRequest) {
     *    return saveBooking(newBooking);
     *  }
     * */
    @Override
    public Mono<Booking> createBooking(Booking newBooking) {
        return saveBooking(newBooking);
    }


    private Mono<Booking> saveBooking(Booking newBooking) {
        return getBookingCount().flatMap(count -> {
            Long countToAdd = count + 1L;
            String bookingRef = String.valueOf(REF_START + countToAdd);
            newBooking.setBookingRef(bookingRef);
            return bookingRepository.save(newBooking).log();
        });
    }

    private Mono<Long> getBookingCount() {
        return bookingRepository.count().log();
    }

    /** Uncomment when availability check needs to be called */
    private boolean checkAvailability(Booking bookingRequest) {
        AvailabilityRequest availabilityRequest = AvailabilityRequest.builder()
                .containerSize(bookingRequest.getContainerSize())
                .containerType(bookingRequest.getContainerType())
                .destination(bookingRequest.getDestination())
                .origin(bookingRequest.getOrigin())
                .quantity(bookingRequest.getQuantity())
                .build();
        return availabilityService.checkAvailability(availabilityRequest);
    }
}
