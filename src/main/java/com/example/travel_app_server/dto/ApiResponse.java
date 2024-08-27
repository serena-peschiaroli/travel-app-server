package com.example.travel_app_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T>{
    private String status;
    private T data;
    private String message;
    private LocalDateTime timestamp;


}