package com.shubham.test.books.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubham.test.books.domain.BookEntity;
import com.shubham.test.books.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private BookEntity testBook;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        
        testBook = new BookEntity();
        testBook.setTitle("Integration Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("9876543210");
        testBook.setPublishedDate(LocalDate.now());
    }

    @Test
    void whenCreateBook_thenStatus201() throws Exception {
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"));
    }

    @Test
    void whenGetAllBooks_thenStatus200() throws Exception {
        BookEntity savedBook = bookRepository.save(testBook);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Integration Test Book")) 
                .andExpect(jsonPath("$.totalElements").exists());  
    }

    @Test
    void whenGetBookById_thenStatus200() throws Exception {
        BookEntity saved = bookRepository.save(testBook);

        mockMvc.perform(get("/api/books/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Test Book"));
    }

    @Test
    void whenGetNonExistingBook_thenStatus404() throws Exception {
        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenUpdateBook_thenStatus200() throws Exception {
        BookEntity saved = bookRepository.save(testBook);
        saved.setTitle("Updated Title");

        mockMvc.perform(put("/api/books/{id}", saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void whenDeleteBook_thenStatus200() throws Exception {
        BookEntity saved = bookRepository.save(testBook);

        mockMvc.perform(delete("/api/books/{id}", saved.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchBooks() throws Exception {
        BookEntity book = new BookEntity();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublishedDate(LocalDate.now());

        bookRepository.save(book);

        mockMvc.perform(get("/api/books/search?searchTerm=Test"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(greaterThan(0)))); 
    }
}