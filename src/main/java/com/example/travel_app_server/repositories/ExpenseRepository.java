package com.example.travel_app_server.repositories;

import com.example.travel_app_server.models.Expense;
import com.example.travel_app_server.models.ExpenseCategory;
import com.example.travel_app_server.models.Stop;
import com.example.travel_app_server.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByTrip(Trip trip);
    List<Expense> findByStop(Stop stop);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.trip = :trip")
   BigDecimal sumAmountByTrip(Trip trip);

    List<Expense> findByCategory(ExpenseCategory category);

}
