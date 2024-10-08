package com.example.travel_app_server.services.impl;

import com.example.travel_app_server.dto.ExpenseDto;
import com.example.travel_app_server.models.Expense;
import com.example.travel_app_server.models.ExpenseCategory;
import com.example.travel_app_server.models.Stop;
import com.example.travel_app_server.models.Trip;
import com.example.travel_app_server.repositories.ExpenseRepository;
import com.example.travel_app_server.repositories.StopRepository;
import com.example.travel_app_server.repositories.TripRepository;
import com.example.travel_app_server.services.ExpenseService;
import com.example.travel_app_server.utils.ExpenseMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Transactional
    //add espense associating it with a trip id and optionally stop id
    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        Trip trip = tripRepository.findById(expenseDto.getTripId())
                .orElseThrow(()->new ResourceNotFoundException("trip not found with id " + expenseDto.getTripId()));

        Stop stop = null;
        if(expenseDto.getStopId() != null){
            stop = stopRepository.findById(expenseDto.getStopId())
                    .orElseThrow(()-> new ResourceNotFoundException("stop not found with id " + expenseDto.getStopId()));

            if(!stop.getTrip().getId().equals(expenseDto.getTripId())){
                throw new IllegalArgumentException("The provided stop is not associated with this trip");
            }

            //set stop date if given
            expenseDto.setDate(stop.getDate());
        }else{
            //if not, set trip date
            LocalDate tripDate = trip.getStartDate();
            expenseDto.setDate(tripDate.atStartOfDay());
        }

        if (expenseDto.getCategory() == null) {
            throw new IllegalArgumentException("Category is required for the expense.");
        }



        Expense expense = ExpenseMapper.toEntity(expenseDto, trip, stop);
        Expense savedExpense = expenseRepository.save(expense);

        return ExpenseMapper.toDto(savedExpense);
    }

    @Override
    public ExpenseDto getExpenseById(Long id) {


        //fetch expense
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Expense not found with id " + id));

        //convert
        return ExpenseMapper.toDto(expense);
    }

    @Override
    //retrieve all expenses within a specific trip
    public List<ExpenseDto> getExpensesByTripId(Long tripId) {
        //fetch trip
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(()->new ResourceNotFoundException("trip not found " + tripId));


        //fetch associated expenses
        List<Expense> expenses = expenseRepository.findByTrip(trip);

        return expenses.stream().map(ExpenseMapper::toDto).collect(Collectors.toList());
    }

    @Override
    //retrieve all expenses within a specific stop
    public List<ExpenseDto> getExpensesByStopId(Long stopId) {
        Stop stop = stopRepository.findById(stopId)
                .orElseThrow(()-> new ResourceNotFoundException("Stop not found with id " + stopId));

        List<Expense> expenses = expenseRepository.findByStop(stop);
        return expenses.stream().map(ExpenseMapper::toDto).collect(Collectors.toList());
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

        if (expenseDto.getCategory() == null) {
            throw new IllegalArgumentException("Category is required for the expense.");
        }



        existingExpense.setAmount(expenseDto.getAmount());
        existingExpense.setDescription(expenseDto.getDescription());
        existingExpense.setDate(expenseDto.getDate());
        existingExpense.setTrip(trip);
        existingExpense.setStop(stop);
        existingExpense.setCategory(expenseDto.getCategory());

        Expense updateExpense = expenseRepository.save(existingExpense);

        return ExpenseMapper.toDto(updateExpense);
    }

    @Override
    public void deleteExpense(Long id) {

        if(!expenseRepository.existsById(id)){
            throw new ResourceNotFoundException("Expense not found with id" + id);
        }

        expenseRepository.deleteById(id);


    }

    @Override
    public List<ExpenseDto> getAllExpensesByCategory(ExpenseCategory category) {
        List<Expense> expenses = expenseRepository.findByCategory(category);
        return expenses.stream().map(ExpenseMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    //add expense to a specific trip
    public List<ExpenseDto> addExpensesToTrip(Long tripId, List<ExpenseDto> expenses) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id " + tripId));

        // fetcj all stop ids from expenses
        Set<Long> stopIds = expenses.stream()
                .filter(expenseDto -> expenseDto.getStopId() != null)
                .map(ExpenseDto::getStopId)
                .collect(Collectors.toSet());

        // get all stops and store them in a map by id
        Map<Long, Stop> stops = stopRepository.findAllById(stopIds)
                .stream()
                .collect(Collectors.toMap(Stop::getId, stop -> stop));

        List<Expense> savedExpenses = expenses.stream().map(expenseDto -> {
            Stop stop = stops.get(expenseDto.getStopId());
            if (stop != null && !stop.getTrip().getId().equals(tripId)) {
                throw new IllegalArgumentException("The provided stop is not associated with this trip");
            }

            LocalDateTime expenseDate = (stop != null) ? stop.getDate() : trip.getStartDate().atStartOfDay();
            expenseDto.setDate(expenseDate);

            if (expenseDto.getCategory() == null) {
                throw new IllegalArgumentException("Category is required for the expense.");
            }

            Expense expense = ExpenseMapper.toEntity(expenseDto, trip, stop);
            return expenseRepository.save(expense);
        }).collect(Collectors.toList());

        return savedExpenses.stream().map(ExpenseMapper::toDto).collect(Collectors.toList());
    }
}
