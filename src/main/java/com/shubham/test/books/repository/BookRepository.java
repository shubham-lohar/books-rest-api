package com.shubham.test.books.repository;

import com.shubham.test.books.domain.BookEntity;
import com.shubham.test.books.exceptions.BookNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    default BookEntity findByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<BookEntity> searchByTitleOrAuthor(@Param("searchTerm") String searchTerm);
}