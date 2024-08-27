package com.example.travel_app_server.services.impl;

import com.example.travel_app_server.dto.TripDto;
import com.example.travel_app_server.models.Trip;
import com.example.travel_app_server.repositories.TripRepository;
import com.example.travel_app_server.services.TripService;
import com.example.travel_app_server.utils.TripMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;
    @Override
    public TripDto addTrip(TripDto tripDto) {
        Trip trip = TripMapper.toEntity(tripDto);
        Trip savedTrip = tripRepository.save(trip);
        return TripMapper.toDto(savedTrip);
    }

    @Override
    public TripDto getTripById(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id " + id));
        return TripMapper.toDto(trip);
    }

    @Override
    public List<TripDto> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream()
                .map(TripMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    @Override
    public TripDto updateTrip(TripDto tripDto) {
        Trip trip = TripMapper.toEntity(tripDto);
        Trip updatedTrip = tripRepository.save(trip);
        return TripMapper.toDto(updatedTrip);
    }



}
