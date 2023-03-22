package com.maersk.cbm.containerbookingmanager.repository;

import com.maersk.cbm.containerbookingmanager.model.Booking;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends ReactiveCassandraRepository<Booking, String> {
}
