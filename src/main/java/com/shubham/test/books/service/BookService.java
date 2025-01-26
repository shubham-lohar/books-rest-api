package com.shubham.test.books.service;

import com.shubham.test.books.domain.BookEntity;
import com.shubham.test.books.repository.BookRepository;
import com.shubham.test.books.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

    public BookEntity addBook(BookEntity book) {
        return bookRepository.save(book);
    }

    public Page<BookEntity> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public BookEntity getBookById(Long id) {
        return bookRepository.findByIdOrThrow(id);
    }

    public BookEntity updateBook(Long id, BookEntity bookDetails) {
        BookEntity book = bookRepository.findByIdOrThrow(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublishedDate(bookDetails.getPublishedDate());
        book.setIsbn(bookDetails.getIsbn());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.findByIdOrThrow(id);
        bookRepository.deleteById(id);
    }

    public List<BookEntity> searchBooks(String searchTerm) {
        return bookRepository.searchByTitleOrAuthor(searchTerm);
    }
}