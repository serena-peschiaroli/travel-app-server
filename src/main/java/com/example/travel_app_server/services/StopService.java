package com.example.travel_app_server.services;

import com.example.travel_app_server.dto.StopDto;
import com.example.travel_app_server.dto.TripDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StopService
{
    StopDto addStop(Long tripId, StopDto stopDto);
    StopDto getStopById(Long id);
    List<StopDto> getStopsByTripId(Long tripId);

    void deleteStop(Long id);
    StopDto updateStop(StopDto stopDto);

}
