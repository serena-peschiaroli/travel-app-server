package com.example.travel_app_server.repositories;

import com.example.travel_app_server.models.Stop;
import com.example.travel_app_server.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

    List<Stop> findByTrip(Trip trip);
}
