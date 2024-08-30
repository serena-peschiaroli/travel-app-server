package com.example.travel_app_server.dto;

import com.example.travel_app_server.models.Expense;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {

    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    private String description;
    @NotNull(message = "Start date is mandatory")
    private LocalDate startDate;
    @NotNull(message = "End date is mandatory")
    private LocalDate endDate;
    private List<StopDto> stops;
    private List<ExpenseDto> expenses;
    private List<CategoryDto> categories;
}
