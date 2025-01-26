package com.shubham.test.books.controller;

import com.shubham.test.books.domain.BookEntity;
import com.shubham.test.books.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public BookEntity createBook(@Valid @RequestBody BookEntity book) {
        return bookService.addBook(book);
    }

    @GetMapping
    public Page<BookEntity> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
        
    }

    @PutMapping("/{id}")
    public BookEntity updateBook(@PathVariable Long id, @Valid @RequestBody BookEntity bookDetails) {
        return bookService.updateBook(id, bookDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<BookEntity> searchBooks(@RequestParam String searchTerm) {
        return bookService.searchBooks(searchTerm);
    }
}

