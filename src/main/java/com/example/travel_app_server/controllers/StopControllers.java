package com.example.travel_app_server.controllers;

import com.example.travel_app_server.dto.ApiResponse;
import com.example.travel_app_server.dto.StopDto;
import com.example.travel_app_server.services.StopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/trips/{tripsId}/stops")
public class StopControllers {

    @Autowired
    private StopService stopService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StopDto>>> getAllStopsByTrip(@PathVariable Long tripId){
        List<StopDto> stops = stopService.getStopsByTripId(tripId);
        ApiResponse<List<StopDto>> response = ApiResponse.<List<StopDto>>builder()
                .status("success")
                .data(stops)
                .message("Stops retrieved successfully")
                .timestamp(LocalDateTime.now())
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
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StopDto>> addStop(@PathVariable Long tripId, @RequestBody StopDto stopDto){
        StopDto createdStop = stopService.addStop(tripId, stopDto);
        ApiResponse<StopDto> response = ApiResponse.<StopDto>builder()
                .status("success")
                .data(createdStop)
                .message("Stop created successfully")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{stopId}")
    public ResponseEntity<ApiResponse<StopDto>> updateStop(@PathVariable Long tripId, @PathVariable Long stopId, @RequestBody StopDto stopDto){
        stopDto.setId(stopId);
        StopDto updatedStop = stopService.updateStop(stopDto);
        ApiResponse<StopDto> response = ApiResponse.<StopDto>builder()
                .status("success")
                .data(updatedStop)
                .message("Stop updated successfully")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @DeleteMapping("/{stopId}")
    public ResponseEntity<ApiResponse<Void>> deleteStop(@PathVariable Long tripId, @PathVariable Long stopId){
        stopService.deleteStop(stopId);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status("success")
                .message("Stop deleted successfully")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
