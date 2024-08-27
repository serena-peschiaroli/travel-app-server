package com.example.travel_app_server.utils;

import com.example.travel_app_server.dto.TripDto;
import com.example.travel_app_server.models.Trip;

public class TripMapper {

    public static TripDto toDto(Trip trip) {
        if (trip == null) {
            return null;
        }

        return TripDto.builder()
                .id(trip.getId())
                .title(trip.getTitle())
                .description(trip.getDescription())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .build();
    }


    public static Trip toEntity(TripDto tripDto) {
        if (tripDto == null) {
            return null;
        }

        return Trip.builder()
                .id(tripDto.getId())
                .title(tripDto.getTitle())
                .description(tripDto.getDescription())
                .startDate(tripDto.getStartDate())
                .endDate(tripDto.getEndDate())
                .build();
    }
}
