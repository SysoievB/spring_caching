package com.caching.service;

import com.caching.exception.BookNotFoundException;
import com.caching.model.Book;
import com.caching.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo repo;

    @Cacheable(value = "books", key = "#id")
    public Optional<Book> findBookById(Long id) {
        log.info("--------------Fetching from DB--------------");
        return repo.findById(id);
    }

    @Cacheable(value = "books")
    public List<Book> findAllBooks() {
        log.info("--------------Fetching from DB--------------");
        return repo.findAll();
    }

    @Caching(evict = { @CacheEvict(value = "books", allEntries = true) },
            put = { @CachePut(value = "books", key = "#result.id") })
    public Book saveBook(Book book) {
        log.info("Saving book to DB");
        return repo.save(book);
    }

    @Caching(evict = { @CacheEvict(value = "books", allEntries = true) },
            put = { @CachePut(value = "books", key = "#id") })
    public Book updateBook(Long id, Book newBook) {
        val oldBook = repo.findById(id)
                .orElseThrow(BookNotFoundException::new);

        val updatedBook = oldBook.toBuilder()
                .name(newBook.getName())
                .author(newBook.getAuthor())
                .publishDate(newBook.getPublishDate())
                .description(newBook.getDescription())
                .build();

        return repo.save(updatedBook);
    }

    @CacheEvict(value = "books", allEntries = true)
    public void deleteBook(Long id) {
        log.info("Deleting book with id {} from DB", id);
        if (id == null) throw new IllegalArgumentException("Book id cannot be null.");
        findBookById(id);  // This is to check if the book exists in the DB and throw an exception if it doesn't.
        repo.deleteById(id);
    }
}
