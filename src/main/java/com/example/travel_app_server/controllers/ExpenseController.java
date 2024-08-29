package com.example.travel_app_server.controllers;

import com.example.travel_app_server.dto.ApiResponse;
import com.example.travel_app_server.dto.ExpenseDto;
import com.example.travel_app_server.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseDto>> getExpenseById(@PathVariable Long id) {
        ExpenseDto expense = expenseService.getExpenseById(id);
        ApiResponse<ExpenseDto> response = ApiResponse.<ExpenseDto>builder()
                .status("success")
                .data(expense)
                .message("Expense retrieved successfully")
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<ApiResponse<List<ExpenseDto>>> getExpensesByTripId(@PathVariable Long tripId) {
        List<ExpenseDto> expenses = expenseService.getExpensesByTripId(tripId);
        ApiResponse<List<ExpenseDto>> response = ApiResponse.<List<ExpenseDto>>builder()
                .status("success")
                .data(expenses)
                .message("Expenses retrieved successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseDto>> addExpense(@Valid @RequestBody ExpenseDto expenseDto) {
        ExpenseDto createdExpense = expenseService.addExpense(expenseDto);
        ApiResponse<ExpenseDto> response = ApiResponse.<ExpenseDto>builder()
                .status("success")
                .data(createdExpense)
                .message("Expense created successfully")
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseDto>> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseDto expenseDto) {
        expenseDto.setId(id);
        ExpenseDto updatedExpense = expenseService.updateExpense(id, expenseDto);
        ApiResponse<ExpenseDto> response = ApiResponse.<ExpenseDto>builder()
                .status("success")
                .data(updatedExpense)
                .message("Expense updated successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status("success")
                .message("Expense deleted successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
