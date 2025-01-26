package com.shubham.test.books.service;

import com.shubham.test.books.domain.BookEntity;
import com.shubham.test.books.repository.BookRepository;
import com.shubham.test.books.exceptions.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private BookEntity testBook;

    @BeforeEach
    void setUp() {
        testBook = new BookEntity();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("1234567890");
        testBook.setPublishedDate(LocalDate.now());
    }

    @Test
    void whenAddBook_thenBookShouldBeSaved() {
        when(bookRepository.save(any(BookEntity.class))).thenReturn(testBook);
        BookEntity savedBook = bookService.addBook(testBook);
        assertNotNull(savedBook);
        assertEquals("Test Book", savedBook.getTitle());
        verify(bookRepository).save(any(BookEntity.class));
    }

    @Test
    void whenGetAllBooks_thenShouldReturnList() {
        Pageable pageable = PageRequest.of(0, 10);
        
        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(Arrays.asList(testBook)));
        
        Page<BookEntity> found = bookService.getAllBooks(pageable);
        
        assertNotNull(found);
        assertEquals(1, found.getTotalElements());
        verify(bookRepository).findAll(pageable);
    }

    @Test
    void whenGetValidId_thenBookShouldBeFound() {
        when(bookRepository.findByIdOrThrow(1L)).thenReturn(testBook);
        BookEntity found = bookService.getBookById(1L);
        assertNotNull(found);
        assertEquals(1L, found.getId());
        verify(bookRepository).findByIdOrThrow(1L);
    }

    @Test
    void whenGetInvalidId_thenThrowException() {
        when(bookRepository.findByIdOrThrow(999L)).thenThrow(new BookNotFoundException(999L));
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(999L));
        verify(bookRepository).findByIdOrThrow(999L);
    }

    @Test
    void whenUpdateBook_thenBookShouldBeUpdated() {
        BookEntity updatedBook = new BookEntity();
        updatedBook.setTitle("Updated Title");
        when(bookRepository.findByIdOrThrow(1L)).thenReturn(testBook);
        when(bookRepository.save(any(BookEntity.class))).thenReturn(updatedBook);
        
        BookEntity result = bookService.updateBook(1L, updatedBook);
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(bookRepository).findByIdOrThrow(1L);
        verify(bookRepository).save(any(BookEntity.class));
    }

    @Test
    void whenDeleteBook_thenBookShouldBeDeleted() {
        when(bookRepository.findByIdOrThrow(1L)).thenReturn(testBook);
        when(bookRepository.existsById(1L)).thenReturn(true); 
        doNothing().when(bookRepository).deleteById(1L);
        
      
        bookService.deleteBook(1L);
        

        verify(bookRepository).findByIdOrThrow(1L);
        verify(bookRepository).deleteById(1L);
    }
}