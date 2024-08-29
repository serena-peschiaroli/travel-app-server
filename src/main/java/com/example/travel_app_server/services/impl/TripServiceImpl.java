package com.example.travel_app_server.services.impl;

import com.example.travel_app_server.dto.CategoryDto;
import com.example.travel_app_server.dto.TripDto;
import com.example.travel_app_server.models.Category;
import com.example.travel_app_server.models.Trip;
import com.example.travel_app_server.repositories.CategoryRepository;
import com.example.travel_app_server.repositories.TripRepository;
import com.example.travel_app_server.services.CategoryService;
import com.example.travel_app_server.services.TripService;
import com.example.travel_app_server.utils.TripMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Override
    @Transactional
    public TripDto addTrip(TripDto tripDto) {
        List<Category> categories = categoryRepository.findAllById(
                tripDto.getCategories().stream().map(CategoryDto::getId).collect(Collectors.toList())
        );
        Trip trip = TripMapper.toEntity(tripDto);
        Trip savedTrip = tripRepository.save(trip);
        return TripMapper.toDto(savedTrip);
    }

    @Override
    public TripDto getTripById(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id " + id));
        return TripMapper.toDto(trip);
    }

    @Override
    public List<TripDto> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream()
                .map(TripMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TripDto updateTrip(TripDto tripDto) {
        Trip existingTrip = tripRepository.findById(tripDto.getId())
                .orElseThrow(()->new ResourceNotFoundException("Trip not found with id " + tripDto.getId()));

        existingTrip.setTitle(tripDto.getTitle());
        existingTrip.setDescription(tripDto.getDescription());
        existingTrip.setStartDate(tripDto.getStartDate());
        existingTrip.setEndDate(tripDto.getEndDate());

        List<Long> categoryIds = tripDto.getCategories().stream().map(CategoryDto::getId).collect(Collectors.toList());

        List<Category> newCategories = categoryRepository.findAllById(categoryIds);
        List<Category> updatedCategories = existingTrip.getCategories();
        for (Category category : newCategories){
            if (!updatedCategories.contains(category)){
                updatedCategories.add(category);
            }
        }

        existingTrip.setCategories(updatedCategories);
        Trip updatedTrip = tripRepository.save(existingTrip);
        return TripMapper.toDto(updatedTrip);
    }



}
