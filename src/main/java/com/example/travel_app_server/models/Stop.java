package com.example.travel_app_server.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Location is mandatory")
    private String location;
    @Column(length = 1000)
    private String description;
    @ElementCollection
    @CollectionTable(name = "stop_curiosities", joinColumns = @JoinColumn(name = "stop_id"))
    private List <String> curiosities;
    @ElementCollection
    @CollectionTable(name = "stop_photos", joinColumns = @JoinColumn(name = "stop_id"))
    @Column(name = "photo_path")
    private List<String> photos;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @OneToMany(mappedBy = "stop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;

}
