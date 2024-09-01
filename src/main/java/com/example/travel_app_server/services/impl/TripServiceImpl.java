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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Override
    // ad new trip record to the db
    @Transactional
    public TripDto addTrip(TripDto tripDto) {
        //fetch categories by id and create a list
        List<Category> categories = categoryRepository.findAllById(
                tripDto.getCategories().stream().map(CategoryDto::getId).collect(Collectors.toList())
        );
        //convert dto to entity and save to db
        Trip trip = TripMapper.toEntity(tripDto);
        Trip savedTrip = tripRepository.save(trip);
        //returns savedtrp to dto
        return TripMapper.toDto(savedTrip);
    }

    @Override
    //fetch trip by id or throws if not found
    public TripDto getTripById(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id " + id));
        return TripMapper.toDto(trip);
    }

    @Override
    //retrieve all the trips from db and convert them to dtos
    public List<TripDto> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream()
                .map(TripMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    //delte trip by id
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    @Override
    @Transactional
    //update existing trip with new data
    public TripDto updateTrip(TripDto tripDto) {
        Trip existingTrip = tripRepository.findById(tripDto.getId())
                .orElseThrow(()->new ResourceNotFoundException("Trip not found with id " + tripDto.getId()));
        //update attributes
        existingTrip.setTitle(tripDto.getTitle());
        existingTrip.setDescription(tripDto.getDescription());
        existingTrip.setStartDate(tripDto.getStartDate());
        existingTrip.setEndDate(tripDto.getEndDate());

        //update categories associated with the trip
        List<Long> categoryIds = tripDto.getCategories().stream().map(CategoryDto::getId).distinct().collect(Collectors.toList());

//        List<Category> newCategories = categoryRepository.findAllById(categoryIds);
        List<Category> updatedCategories = existingTrip.getCategories();
//        for (Category category : newCategories){
//            if (!updatedCategories.contains(category)){
//                updatedCategories.add(category);
//            }
//        }

        //update the categories associated with the trip
        //remove duplicate
        Set<Category> newCategories = new HashSet<>(categoryRepository.findAllById(categoryIds));
        existingTrip.setCategories(new ArrayList<>(newCategories));
        existingTrip.setCategories(updatedCategories);
        Trip updatedTrip = tripRepository.save(existingTrip);
        return TripMapper.toDto(updatedTrip);
    }

    @Override
    //retrieve all trips with specific category id
    public List<TripDto> getTripsByCategoryId(Long categoryId) {
        //find category
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("caategory not found with id" + categoryId));

        //get all trips associated with categoryId
        List<Trip> trips = tripRepository.findByCategoriesContaining(category);

        //convert the list to dto

        return trips.stream().map(TripMapper::toDto).collect(Collectors.toList());
    }




}
