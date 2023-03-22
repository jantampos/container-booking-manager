package com.maersk.cbm.containerbookingmanager.service;

import com.maersk.cbm.containerbookingmanager.model.dtos.AvailabilityRequest;

public interface IAvailabilityService{

    boolean checkAvailability(AvailabilityRequest availabilityRequest);
}
