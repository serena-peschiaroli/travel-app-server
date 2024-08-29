package com.example.travel_app_server.services.impl;

import com.example.travel_app_server.dto.StopDto;
import com.example.travel_app_server.models.Stop;
import com.example.travel_app_server.models.Trip;
import com.example.travel_app_server.repositories.StopRepository;
import com.example.travel_app_server.repositories.TripRepository;
import com.example.travel_app_server.services.StopService;
import com.example.travel_app_server.utils.StopMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StopServiceImpl implements StopService {
    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private TripRepository tripRepository;

    @Override
    @Transactional
    public StopDto addStop(Long tripId, StopDto stopDto) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()-> new ResourceNotFoundException("Trip not found with id " + tripId));

        Stop stop = StopMapper.toEntity(stopDto, trip);

        Stop savedStop = stopRepository.save(stop);

        return StopMapper.toDto(savedStop);

    }

    @Override
    public StopDto getStopById(Long id) {
        Stop stop = stopRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Stop not found with id " + id));

        return StopMapper.toDto(stop);
    }

    @Override
    public List<StopDto> getStopsByTripId(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id" + tripId));

        List<Stop> stops = stopRepository.findByTrip(trip);
        return stops.stream().map(StopMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional

    public void deleteStop(Long id) {
        if(!stopRepository.existsById(id)){
            throw new ResourceNotFoundException("Stop not found with id" + id);
        }
        stopRepository.deleteById(id);

    }

    @Override
    @Transactional
    public StopDto updateStop(StopDto stopDto) {

        Stop existingStop = stopRepository.findById(stopDto.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Stop not found with id " + stopDto
                        .getId()));
        Trip trip = tripRepository.findById(stopDto.getTripId())
                .orElseThrow(()-> new ResourceNotFoundException("Trip not found with id" + stopDto.getTripId()));

        existingStop.setTitle(stopDto.getTitle());
        existingStop.setLocation(stopDto.getLocation());
        existingStop.setDescription(stopDto.getDescription());
        existingStop.setPhotos(stopDto.getPhotos());
        existingStop.setDate(stopDto.getDate());
        existingStop.setTrip(trip);

        Stop updateStop = stopRepository.save(existingStop);

        return StopMapper.toDto(updateStop);
    }
}
