package com.example.travel_app_server.controllers;

import com.example.travel_app_server.dto.ApiResponse;
import com.example.travel_app_server.dto.ExpenseDto;
import com.example.travel_app_server.dto.TripDto;
import com.example.travel_app_server.services.ExpenseService;
import com.example.travel_app_server.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "http://localhost:5173")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ApiResponse<TripDto>> addTrip(@RequestBody TripDto tripDto) {
        TripDto createdTrip = tripService.addTrip(tripDto);
        ApiResponse<TripDto> response = ApiResponse.<TripDto>builder()
                .status("success")
                .data(createdTrip)
                .message("Trip created successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/expenses")
    public ResponseEntity<ApiResponse<List<ExpenseDto>>> addExpensesToTrip(@PathVariable Long id, @RequestBody List<ExpenseDto> expenses) {
        List<ExpenseDto> addedExpenses = expenseService.addExpensesToTrip(id, expenses);
        ApiResponse<List<ExpenseDto>> response = ApiResponse.<List<ExpenseDto>>builder()
                .status("success")
                .data(addedExpenses)
                .message("Expenses added to trip successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TripDto>> getTripById(@PathVariable Long id) {
        TripDto trip = tripService.getTripById(id);
        ApiResponse<TripDto> response = ApiResponse.<TripDto>builder()
                .status("success")
                .data(trip)
                .message("Trip retrieved successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TripDto>>> getAllTrips() {
        List<TripDto> trips = tripService.getAllTrips();
        ApiResponse<List<TripDto>> response = ApiResponse.<List<TripDto>>builder()
                .status("success")
                .data(trips)
                .message("Trips retrieved successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TripDto>> updateTrip(@PathVariable Long id, @RequestBody TripDto tripDto) {
        tripDto.setId(id);
        TripDto updatedTrip = tripService.updateTrip(tripDto);
        ApiResponse<TripDto> response = ApiResponse.<TripDto>builder()
                .status("success")
                .data(updatedTrip)
                .message("Trip updated successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status("success")
                .message("Trip deleted successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
