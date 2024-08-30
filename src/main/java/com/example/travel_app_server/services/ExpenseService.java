package com.example.travel_app_server.services;

import com.example.travel_app_server.dto.ExpenseDto;
import com.example.travel_app_server.models.Expense;
import com.example.travel_app_server.models.ExpenseCategory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface ExpenseService {

    BigDecimal getTotalExpensesForTrip(Long tripId);

    BigDecimal getTotalExpensesForTrips(List<Long> tripIds);

    ExpenseDto addExpense(ExpenseDto expenseDto);

    ExpenseDto getExpenseById(Long id);

    List<ExpenseDto> getExpensesByTripId(Long tripId);

    List<ExpenseDto> getExpensesByStopId(Long stopId);

    ExpenseDto updateExpense(Long expenseId, ExpenseDto expenseDto);

    void deleteExpense(Long id);

    List<ExpenseDto> getAllExpensesByCategory(ExpenseCategory category);





}
