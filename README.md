# Travel App Server

The Travel App Server is a backend service designed to manage travel-related data, including trips, stops, expenses, and categories. It serves as the central API for a travel application, facilitating CRUD operations and providing business logic support.

## Technologies
- **Spring Boot**: Framework for creating stand-alone, production-grade Spring-based applications easily.
- **Spring Data JPA**: Simplifies data access operations, improving consistency across JPA repositories.
- **Spring Data REST**: Leverages Spring Data repositories to expose them as RESTful resources automatically.
- **PostgreSQL**: Used as the primary database for storing application data.
- **Lombok**: Provides annotations to minimize boilerplate code in Java applications.
- **Jakarta Validation**: Used for validating constraints in Java Bean components.

## API Endpoints

### Trips
- `GET /api/trips` - Retrieve all trips.
- `POST /api/trips` - Create a new trip.
- `GET /api/trips/{id}` - Get trip details by ID.
- `PUT /api/trips/{id}` - Update trip details by ID.
- `DELETE /api/trips/{id}` - Delete a trip by ID.
- `POST /api/trips/{id}/expenses` - Add multiple expenses to a trip.

### Stops
- `GET /api/trips/{tripId}/stops` - Retrieve all stops for a specific trip.
- `GET /api/trips/{tripId}/stops/{stopId}` - Get a specific stop by ID.
- `POST /api/trips/{tripId}/stops` - Add a stop to a specific trip.
- `PUT /api/trips/{tripId}/stops/{stopId}` - Update a stop.
- `DELETE /api/trips/{tripId}/stops/{stopId}` - Delete a stop by ID.

### Expenses
- `GET /api/expenses/{id}` - Retrieve an expense by ID.
- `GET /api/expenses/trip/{tripId}` - Get all expenses for a specific trip.
- `POST /api/expenses` - Add an expense.
- `PUT /api/expenses/{id}` - Update an expense.
- `DELETE /api/expenses/{id}` - Delete an expense by ID.
- `GET /api/expenses/category/{category}` - Retrieve expenses by category.

### Categories
- `GET /api/categories` - Retrieve all categories.
- `POST /api/categories` - Create a new category.
- `GET /api/categories/{id}` - Get a category by ID.
- `PUT /api/categories/{id}` - Update a category.
- `DELETE /api/categories/{id}` - Delete a category.
- `GET /api/categories/{id}/trips` - Retrieve all trips associated with a category.
