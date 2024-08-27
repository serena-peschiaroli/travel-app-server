package com.example.travel_app_server.services.impl;

import com.example.travel_app_server.dto.ExpenseDto;
import com.example.travel_app_server.models.Expense;
import com.example.travel_app_server.models.Stop;
import com.example.travel_app_server.models.Trip;
import com.example.travel_app_server.repositories.ExpenseRepository;
import com.example.travel_app_server.repositories.StopRepository;
import com.example.travel_app_server.repositories.TripRepository;
import com.example.travel_app_server.services.ExpenseService;
import com.example.travel_app_server.utils.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private StopRepository stopRepository;

    @Override
    public BigDecimal getTotalExpensesForTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()->new ResourceNotFoundException("Trip not found with id" + tripId));
        return expenseRepository.sumAmountByTrip(trip);
    }

    @Override
    public BigDecimal getTotalExpensesForTrips(List<Long> tripIds) {

        return tripIds.stream().map(this::getTotalExpensesForTrip).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        Trip trip = tripRepository.findById(expenseDto.getTripId())
                .orElseThrow(()->new ResourceNotFoundException("trip not found with id " + expenseDto.getTripId()));

        Stop stop = null;
        if(expenseDto.getStopId() != null){
            stop = stopRepository.findById(expenseDto.getStopId())
                    .orElseThrow(()-> new ResourceNotFoundException("stop not found with id " + expenseDto.getStopId()));
        }

        Expense expense = ExpenseMapper.toEntity(expenseDto, trip, stop);
        Expense savedExpense = expenseRepository.save(expense);

        return ExpenseMapper.toDto(savedExpense);
    }

    @Override
    public ExpenseDto getExpenseById(Long id) {
        return null;
    }

    @Override
    public List<ExpenseDto> getExpensesByTripId(Long tripId) {
        return null;
    }

    @Override
    public List<ExpenseDto> getExpensesByStopId(Long stopId) {
        return null;
    }

    @Override
    public ExpenseDto updateExpense(Long expenseId, ExpenseDto expenseDto) {
        Expense existingExpense = expenseRepository.findById(expenseId)
                .orElseThrow(()->new ResourceNotFoundException("Expense not found with id  " + expenseId));

        Trip trip = tripRepository.findById(expenseDto.getTripId())
                .orElseThrow(()->new ResourceNotFoundException("Trip not found with id " + expenseDto.getTripId()));

        Stop stop = null;
        if(expenseDto.getStopId()!= null){
            stop = stopRepository.findById(expenseDto.getStopId())
                    .orElseThrow(()-> new ResourceNotFoundException("Stop not found with id " + expenseDto.getStopId()));
        }

        existingExpense.setAmount(expenseDto.getAmount());
        existingExpense.setDescription(expenseDto.getDescription());
        existingExpense.setDate(expenseDto.getDate());
        existingExpense.setTrip(trip);
        existingExpense.setStop(stop);

        Expense updateExpense = expenseRepository.save(existingExpense);

        return ExpenseMapper.toDto(updateExpense);
    }

    @Override
    public void deleteExpense(Long id) {

    }
}
