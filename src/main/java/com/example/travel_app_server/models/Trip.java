package com.example.travel_app_server.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    private String description;
    @NotNull(message = "Start date is mandatory")
    private LocalDate startDate;
    @NotNull(message = "End date is mandatory")
    private LocalDate endDate;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stop> stops;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;

    @ManyToMany
    @JoinTable(
            name = "category_trip",
            joinColumns = @JoinColumn(name="trip_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;


}
