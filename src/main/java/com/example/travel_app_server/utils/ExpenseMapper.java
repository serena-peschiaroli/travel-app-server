package com.example.travel_app_server.utils;

import com.example.travel_app_server.dto.ExpenseDto;
import com.example.travel_app_server.models.Expense;
import com.example.travel_app_server.models.Stop;
import com.example.travel_app_server.models.Trip;

public class ExpenseMapper {

    public static ExpenseDto toDto(Expense expense){
        return ExpenseDto.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .date(expense.getDate())
                .tripId(expense.getTrip().getId())
                .stopId(expense.getStop() != null ? expense.getStop().getId() : null)
                .category(expense.getCategory())
                .build();
    }

    public static Expense toEntity(ExpenseDto expenseDto, Trip trip, Stop stop){
        return Expense.builder()
                .id(expenseDto.getId())
                .amount(expenseDto.getAmount())
                .description(expenseDto.getDescription())
                .date(expenseDto.getDate())
                .trip(trip)
                .stop(stop)
                .category(expenseDto.getCategory())
                .build();
    }
}
