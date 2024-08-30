package com.example.travel_app_server.controllers;

import com.example.travel_app_server.dto.ApiResponse;
import com.example.travel_app_server.dto.CategoryDto;
import com.example.travel_app_server.dto.TripDto;
import com.example.travel_app_server.models.Trip;
import com.example.travel_app_server.services.CategoryService;
import com.example.travel_app_server.services.TripService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TripService tripService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.addCategory(categoryDto);
        ApiResponse<CategoryDto> response = ApiResponse.<CategoryDto>builder()
                .status("success")
                .data(createdCategory)
                .message("Category created successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getCategoryById(id);
        ApiResponse<CategoryDto> response = ApiResponse.<CategoryDto>builder()
                .status("success")
                .data(category)
                .message("Category retrieved successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        ApiResponse<List<CategoryDto>> response = ApiResponse.<List<CategoryDto>>builder()
                .status("success")
                .data(categories)
                .message("Categories retrieved successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
        ApiResponse<CategoryDto> response = ApiResponse.<CategoryDto>builder()
                .status("success")
                .data(updatedCategory)
                .message("Category updated successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status("success")
                .message("Category deleted successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/trips")
    public ResponseEntity<ApiResponse<List<TripDto>>> getTripsByCategoryId(@PathVariable Long id) {
        List<TripDto> trips = tripService.getTripsByCategoryId(id);
        ApiResponse<List<TripDto>> response = ApiResponse.<List<TripDto>>builder()
                .status("success")
                .data(trips)
                .message("Trips retrieved successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
