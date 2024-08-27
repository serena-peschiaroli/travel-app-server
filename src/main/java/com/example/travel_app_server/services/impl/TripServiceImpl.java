package com.example.travel_app_server.services.impl;

import com.example.travel_app_server.dto.TripDto;
import com.example.travel_app_server.models.Trip;
import com.example.travel_app_server.repositories.TripRepository;
import com.example.travel_app_server.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;
    public TripDto addTrip(TripDto tripDto) {
        Trip trip = convertToEntity(tripDto);
        Trip savedTrip = tripRepository.save(trip);
        return convertToDto(savedTrip);
    }

    @Override
    public TripDto getTripById(Long id) {
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip not found with id " + id));
        return convertToDto(trip);
    }

    @Override
    public List<TripDto> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    @Override
    public TripDto updateTrip(TripDto tripDto) {
        Trip trip = convertToEntity(tripDto);
        Trip updatedTrip = tripRepository.save(trip);
        return convertToDto(updatedTrip);
    }

    // Helper methods to convert between Trip and TripDto

    private TripDto convertToDto(Trip trip) {
        return TripDto.builder()
                .id(trip.getId())
                .title(trip.getTitle())
                .description(trip.getDescription())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .build();
    }

    private Trip convertToEntity(TripDto tripDto) {
        return Trip.builder()
                .id(tripDto.getId())
                .title(tripDto.getTitle())
                .description(tripDto.getDescription())
                .startDate(tripDto.getStartDate())
                .endDate(tripDto.getEndDate())
                .build();
    }


}
