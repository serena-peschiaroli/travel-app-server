package com.example.travel_app_server.controllers;

import com.example.travel_app_server.dto.ApiResponse;
import com.example.travel_app_server.dto.StopDto;
import com.example.travel_app_server.services.FileStorageService;
import com.example.travel_app_server.services.StopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/trips/{tripId}/stops")
@CrossOrigin(origins = "http://localhost:5173")
public class StopControllers {

    @Autowired
    private StopService stopService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StopDto>>> getAllStopsByTrip(@PathVariable Long tripId){
        List<StopDto> stops = stopService.getStopsByTripId(tripId);
        ApiResponse<List<StopDto>> response = ApiResponse.<List<StopDto>>builder()
                .status("success")
                .data(stops)
                .message("Stops retrieved successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{stopId}")
    public ResponseEntity<ApiResponse<StopDto>> getStopById(@PathVariable Long tripId, @PathVariable Long stopId){
        StopDto stop = stopService.getStopById(stopId);
        ApiResponse<StopDto> response = ApiResponse.<StopDto>builder()
                .status("success")
                .data(stop)
                .message("Stop retrieved sccessfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StopDto>> addStop(@PathVariable Long tripId, @RequestPart("stop") StopDto stopDto, @RequestPart("photos")MultipartFile[] files){

        List<String> photoPaths = new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                String filePath = fileStorageService.storeFile(file);
                photoPaths.add(filePath);
            }
        }

        stopDto.setPhotos(photoPaths);

        StopDto createdStop = stopService.addStop(tripId, stopDto);
        ApiResponse<StopDto> response = ApiResponse.<StopDto>builder()
                .status("success")
                .data(createdStop)
                .message("Stop created successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{stopId}")
    public ResponseEntity<ApiResponse<StopDto>> updateStop(@PathVariable Long tripId, @PathVariable Long stopId,
                                                           @Valid @RequestPart("stop") StopDto stopDto,
                                                           @RequestPart(value = "photos", required = false) MultipartFile[] files){
        StopDto existingStop = stopService.getStopById(stopId);

        List<String> newPhotoPaths = new ArrayList<>();
        if(files != null){
            for(MultipartFile file : files){
                String filePath = fileStorageService.storeFile(file);
                newPhotoPaths.add(filePath);
            }
        }

        List<String> updatePhotoPaths = new ArrayList<>(existingStop.getPhotos());
        updatePhotoPaths.addAll(newPhotoPaths);
        stopDto.setPhotos(updatePhotoPaths);

        stopDto.setId(stopId);
        StopDto updatedStop = stopService.updateStop(stopDto);
        ApiResponse<StopDto> response = ApiResponse.<StopDto>builder()
                .status("success")
                .data(updatedStop)
                .message("Stop updated successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/{stopId}")
    public ResponseEntity<ApiResponse<Void>> deleteStop(@PathVariable Long tripId, @PathVariable Long stopId){
        stopService.deleteStop(stopId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status("success")
                .message("Stop deleted successfully")
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
