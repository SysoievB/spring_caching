package com.caching.repository;

import com.caching.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Optional<Book> save(Book book);

    void deleteById(Long id);
}
