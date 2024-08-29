package com.example.travel_app_server.repositories;

import com.example.travel_app_server.models.Category;
import com.example.travel_app_server.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByCategoriesContaining(Category category);
}
