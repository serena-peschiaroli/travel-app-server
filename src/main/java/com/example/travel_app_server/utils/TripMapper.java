package com.example.travel_app_server.utils;

import com.example.travel_app_server.dto.CategoryDto;
import com.example.travel_app_server.dto.ExpenseDto;
import com.example.travel_app_server.dto.StopDto;
import com.example.travel_app_server.dto.TripDto;
import com.example.travel_app_server.models.Category;
import com.example.travel_app_server.models.Expense;
import com.example.travel_app_server.models.Stop;
import com.example.travel_app_server.models.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TripMapper {

    public static TripDto toDto(Trip trip) {
        if (trip == null) {
            return null;
        }
        List<CategoryDto> categoryDtos = trip.getCategories() != null ?
                trip.getCategories().stream().map(CategoryMapper::toDto).collect(Collectors.toList()) :
                new ArrayList<>();

        List<StopDto> stopDtos = trip.getStops() != null ?
                trip.getStops().stream().map(StopMapper::toDto).collect(Collectors.toList()) :
                new ArrayList<>();

        List<ExpenseDto> expenseDtos = trip.getExpenses() != null ?
                trip.getExpenses().stream().map(ExpenseMapper::toDto).collect(Collectors.toList()) :
                new ArrayList<>();

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

        Trip trip = Trip.builder()
                .id(tripDto.getId())
                .title(tripDto.getTitle())
                .description(tripDto.getDescription())
                .startDate(tripDto.getStartDate())
                .endDate(tripDto.getEndDate())
                .build();

        // Map Stops and set reference back to Trip
        trip.setStops(tripDto.getStops() != null ?
                tripDto.getStops().stream().map(stopDto -> StopMapper.toEntity(stopDto, trip)).collect(Collectors.toList()) :
                new ArrayList<>());

        // Map Categories
        trip.setCategories(tripDto.getCategories() != null ?
                tripDto.getCategories().stream().map(CategoryMapper::toEntity).collect(Collectors.toList()) :
                new ArrayList<>());

        return trip;
    }
}
