package com.maersk.cbm.containerbookingmanager;

import com.maersk.cbm.containerbookingmanager.controller.BookingController;
import com.maersk.cbm.containerbookingmanager.service.IAvailabilityService;
import com.maersk.cbm.containerbookingmanager.service.IBookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookingServiceApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BookingController bookingController;

	@Autowired
	private IBookingService bookingService;

	@Autowired
	private IAvailabilityService availabilityService;

	@Test
	public void contextLoads() throws Exception {
		assertThat(restTemplate).isNotNull();
		assertThat(bookingController).isNotNull();
		assertThat(bookingService).isNotNull();
		assertThat(availabilityService).isNotNull();
	}

}
