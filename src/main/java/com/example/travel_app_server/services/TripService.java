package com.example.travel_app_server.services;

import com.example.travel_app_server.models.Trip;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TripService {

    public Trip addTrip(Trip trip);
    public Trip getTripById(Long id);
    public List<Trip> getAllTrips();
    public void deleteTrip(Long id);
}
