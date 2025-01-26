## Overview

This is a Spring Boot REST API for managing books.

## Tasks Done

1. **Endpoints Created**

   - `POST /api/books` - Create a new book
   - `GET /api/books` - Retrieve all books
   - `GET /api/books/{id}` - Retrieve a book by ID
   - `PUT /api/books/{id}` - Update a book by ID
   - `DELETE /api/books/{id}` - Delete a book by ID

2. **Database Support**

   - PostgreSQL for runtime
   - H2 (In-memory) for testing

3. **Error Handling**

   - Returns `404 Not Found` if a book is not found
   - Returns `400 Bad Request` for validation errors

4. **Testing**

   - Unit and Integration Test cases implemented

5. **Bonus Features**
   - Pagination for the `GET /api/books` endpoint
   - Search functionality to filter books by title or author via `GET /search`

## Running the Server Locally

To run the server locally, follow these steps:

1. Ensure you have Java 17 installed on your machine.
2. Clone the repository:
   ```bash
   git clone https://github.com/shubham-lohar/books-rest-api.git
   ```
3. Navigate to the project directory:
   ```bash
   cd books-rest-api
   ```
4. Build the project using Maven:
   ```bash
   ./mvnw clean install
   ```
5. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
6. The server will start on `http://localhost:8080`.

## Running Tests

To run the tests, use the following command:

```bash
./mvnw test
```

## Swagger UI

To view the API documentation, use Swagger UI. Paste `books-openapi.yaml` at [Swagger Editor](https://editor.swagger.io/).
