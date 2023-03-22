package com.maersk.cbm.containerbookingmanager.repository;

import com.maersk.cbm.containerbookingmanager.model.Booking;
import com.maersk.cbm.containerbookingmanager.model.enums.ContainerType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@DataCassandraTest
@ExtendWith(SpringExtension.class)
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void shouldSaveSingleBooking() {
        Booking booking = Booking.builder()
                .bookingRef("957000001")
                .containerType(ContainerType.DRY)
                .containerSize(20)
                .origin("Southampton")
                .destination("Singapore")
                .quantity(5)
                .timestamp("2020-10-12T13:53:09Z")
            .build();

        Publisher<Booking> setup = bookingRepository.deleteAll().thenMany(bookingRepository.save(booking));
        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();
    }
}
