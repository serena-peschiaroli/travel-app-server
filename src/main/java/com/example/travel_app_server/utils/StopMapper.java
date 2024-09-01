package com.example.travel_app_server.utils;

import com.example.travel_app_server.dto.StopDto;
import com.example.travel_app_server.models.Stop;
import com.example.travel_app_server.models.Trip;

public class StopMapper {

    public static StopDto toDto(Stop stop) {
        if (stop == null) {
            return null;
        }

        return StopDto.builder()
                .id(stop.getId())
                .title(stop.getTitle())
                .location(stop.getLocation())
                .description(stop.getDescription())
                .curiosities(stop.getCuriosities())
                .photos(stop.getPhotos())
                .date(stop.getDate())
                .tripId(stop.getTrip() != null ? stop.getTrip().getId() : null)
                .rating(stop.getRating())
                .build();
    }

    public static Stop toEntity(StopDto stopDto, Trip trip) {
        if (stopDto == null) {
            return null;
        }

        return Stop.builder()
                .id(stopDto.getId())
                .title(stopDto.getTitle())
                .location(stopDto.getLocation())
                .description(stopDto.getDescription())
                .curiosities(stopDto.getCuriosities())
                .photos(stopDto.getPhotos())
                .date(stopDto.getDate())
                .trip(trip)
                .rating(stopDto.getRating())
                .build();
    }
}
