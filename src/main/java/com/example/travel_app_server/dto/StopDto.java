package com.example.travel_app_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopDto {
private Long id;
private String location;
private String description;
private List<String> curiosities;
private List<String> photo;
private Date date;
private Long tripId;
}
