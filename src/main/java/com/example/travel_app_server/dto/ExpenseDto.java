package com.example.travel_app_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private Long id;
    private BigDecimal amount;
    private String description;
    private Date date;
    private Long tripId;
    private Long stopId;
}
