package com.example.travel_app_server.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopDto {
private Long id;
@NotBlank(message = "Title is mandatory")
private String title;
@NotBlank(message = "Location is mandatory")
private String location;
private String description;
private List<String> curiosities;
private List<String> photos;
private LocalDateTime date;
@Min(value = 1, message = "Rating must be at least 1")
@Max(value = 5, message = "Rating must be at most 5")
private Integer rating;
private Long tripId;
}
