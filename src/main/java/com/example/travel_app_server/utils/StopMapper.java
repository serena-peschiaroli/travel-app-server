package com.example.travel_app_server.utils;

import com.example.travel_app_server.dto.StopDto;
import com.example.travel_app_server.models.Stop;
import com.example.travel_app_server.models.Trip;

public class StopMapper {

    public static StopDto toDto(Stop stop){
        return StopDto.builder()
                .id(stop.getId())
                .location(stop.getLocation())
                .description(stop.getDescription())
                .curiosities(stop.getCuriosities())
                .photo(stop.getCuriosities())
                .date(stop.getDate())
                .tripId(stop.getTrip().getId())
                .build();

    }

    public static Stop toEntity(StopDto stopDto, Trip trip){
        return Stop.builder()
                .id(stopDto.getId())
                .location(stopDto.getLocation())
                .description(stopDto.getDescription())
                .curiosities(stopDto.getCuriosities())
                .photos(stopDto.getPhoto())
                .date(stopDto.getDate())
                .trip(trip)
                .build();
    }
}
