package com.example.travel_app_server.config;

import com.example.travel_app_server.models.Category;
import com.example.travel_app_server.models.Expense;
import com.example.travel_app_server.models.Stop;
import com.example.travel_app_server.models.Trip;
import com.example.travel_app_server.repositories.CategoryRepository;
import com.example.travel_app_server.repositories.ExpenseRepository;
import com.example.travel_app_server.repositories.StopRepository;
import com.example.travel_app_server.repositories.TripRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (tripRepository.count() == 0) {


            Category personalCategory = new Category();
            personalCategory.setName("Personal");
            personalCategory.setColor("yellow");

            Category workCategory = new Category();
            workCategory.setName("Work");
            workCategory.setColor("orange");


            categoryRepository.saveAll(Arrays.asList(personalCategory, workCategory));



            Trip trip1 = Trip.builder()
                    .title("Ravenna Travel")
                    .description("A two-day visit to Ravenna")
                    .startDate(LocalDate.of(2024, 8, 8))
                    .endDate(LocalDate.of(2024, 8, 10))
                    .categories(Arrays.asList(personalCategory))
                    .build();
            tripRepository.save(trip1);

            Stop stop1 = createStop("Basilica di San Vitale", "Ravenna, Basilica di San Vitale", "Visit the famous basilica.",
                    Arrays.asList("Mosaic of Emperor Justinian", "Unique octagonal shape of the basilica"),
                    Arrays.asList("path_to_photo1", "path_to_photo2"),
                    LocalDateTime.of(2024, 8, 8, 8, 0), 4, trip1);

            Stop stop2 = createStop("Mausoleum of Galla Placidia", "Ravenna, Mausoleum of Galla Placidia", "Explore the mausoleum.",
                    Arrays.asList("Mosaic of the Good Shepherd", "Oldest mosaics in Ravenna"),
                    Arrays.asList("path_to_photo3", "path_to_photo4"),
                    LocalDateTime.of(2024, 8, 8, 12, 0), 3, trip1);
            Stop stop3 = createStop("Piazza del Popolo", "Ravenna, Piazza del Popolo", " walk in Ravenna's central square,",
                    Arrays.asList("Venetian-style columns", "Historic city clock tower"),
                    Arrays.asList("path_to_photo3", "path_to_photo4"),
                    LocalDateTime.of(2024, 8, 8, 16, 0), 4, trip1);
            Stop stop4 = createStop("Piazza del Popolo", "Ravenna, Piazza del Popolo", " walk in Ravenna's central square,",
                    Arrays.asList("Venetian-style columns", "Historic city clock tower"),
                    Arrays.asList("path_to_photo3", "path_to_photo4"),
                    LocalDateTime.of(2024, 8, 9, 10, 0), 3, trip1);
            Stop stop5 = createStop("Archiepiscopal Museum and Chapel", "Ravenna, Archiepiscopal Museum and Chapel", " walk in Ravenna's central square,",
                    Arrays.asList("Ivory Throne of Maximian", "Ancient Christian sarcophagi"),
                    Arrays.asList("path_to_photo3", "path_to_photo4"),
                    LocalDateTime.of(2024, 8, 9, 14, 0),3, trip1);
            Stop stop6 = createStop("Dante's Tomb and Quadrarco of Braccioforte", "Ravenna, Dante's Tomb", " Visit the Archiepiscopal Chapel and museum",
                    Arrays.asList("Tomb of Dante", "Eternal flame maintained by Florence"),
                    Arrays.asList("path_to_photo3", "path_to_photo4"),
                    LocalDateTime.of(2024, 8, 9, 14, 0),3, trip1);
            Stop stop7 = createStop("Basilica di Sant'Apollinare Nuovo", "Ravenna, Basilica di Sant'Apollinare Nuovo", " UNESCO World Heritage site.",
                    Arrays.asList("Mosaics of the procession of saints", "Bell tower dating back to the 9th century"),
                    Arrays.asList("path_to_photo3", "path_to_photo4"),
                    LocalDateTime.of(2024, 8, 9, 16, 0),3, trip1);




            stopRepository.saveAll(Arrays.asList(stop1, stop2, stop3, stop4, stop5, stop6, stop7));


            Expense expense1 = createExpense(new BigDecimal("50.00"), "Car gasoline", LocalDateTime.of(2024, 8, 8, 7, 0), trip1, null);
            Expense expense2 = createExpense(new BigDecimal("120.00"), "Hotel room", LocalDateTime.of(2024, 8, 8, 20, 0), trip1, null);


            Expense expense3 = createExpense(new BigDecimal("10.00"), "Museum ticket", stop1.getDate(), trip1, stop1);
            Expense expense4 = createExpense(new BigDecimal("5.00"), "Audio guide", stop2.getDate(), trip1, stop2);
            Expense expense5 = createExpense(new BigDecimal("35.00"), "Lunch", LocalDateTime.of(2024,8,8,13,0), trip1, null);
            Expense expense6 = createExpense(new BigDecimal("10.00"), "Entrance Ticket", stop5.getDate(), trip1, stop5);
            Expense expense7 = createExpense(new BigDecimal("12.00"), "Cocktail at local bar", LocalDateTime.of(2024,8,8,17,0), trip1, null);
            Expense expense8 = createExpense(new BigDecimal("50.00"), "Dinner", LocalDateTime.of(2024,8,8,19,0), trip1, null);
            Expense expense9 = createExpense(new BigDecimal("25.00"), "Lunch at local cafè", stop6.getDate(), trip1, stop6);





            expenseRepository.saveAll(Arrays.asList(expense1, expense2, expense3, expense4, expense5, expense6, expense7, expense8, expense9));
        }
    }

    private Stop createStop(String title, String location, String description, List<String> curiosities, List<String> photos, LocalDateTime date, Integer rating, Trip trip) {
        return Stop.builder()
                .title(title)
                .location(location)
                .description(description)
                .curiosities(curiosities)
                .photos(photos)
                .date(date)
                .rating(rating)
                .trip(trip)
                .build();
    }

    private Expense createExpense(BigDecimal amount, String description, LocalDateTime date, Trip trip, Stop stop) {
        return Expense.builder()
                .amount(amount)
                .description(description)
                .date(date)
                .trip(trip)
                .stop(stop)
                .build();
    }

}
