package com.example.travel_app_server.services;

import com.example.travel_app_server.dto.TripDto;
import com.example.travel_app_server.models.Trip;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TripService {

    TripDto addTrip(TripDto tripDto);
    TripDto getTripById(Long id);
    List<TripDto> getAllTrips();
    void deleteTrip(Long id);
    TripDto updateTrip(TripDto tripDto);

    List<TripDto> getTripsByCategoryId(Long categoryId);
}
