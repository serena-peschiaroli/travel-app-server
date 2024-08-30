package com.example.travel_app_server.utils;

import com.example.travel_app_server.dto.CategoryDto;
import com.example.travel_app_server.dto.ExpenseDto;
import com.example.travel_app_server.dto.StopDto;
import com.example.travel_app_server.dto.TripDto;
import com.example.travel_app_server.models.Category;
import com.example.travel_app_server.models.Trip;

import java.util.List;
import java.util.stream.Collectors;

public class TripMapper {

    public static TripDto toDto(Trip trip) {
        if (trip == null) {
            return null;
        }
        List<CategoryDto> categoryDtos = trip.getCategories().stream().map(CategoryMapper::toDto).collect(Collectors.toList());
        List<StopDto> stopDtos = trip.getStops().stream().map(StopMapper::toDto).collect(Collectors.toList());
        List<ExpenseDto> expenseDtos = trip.getExpenses().stream().map(ExpenseMapper::toDto).collect(Collectors.toList());

        return TripDto.builder()
                .id(trip.getId())
                .title(trip.getTitle())
                .description(trip.getDescription())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .categories(categoryDtos)
                .stops(stopDtos)
                .expenses(expenseDtos)
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
