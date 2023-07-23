package com.caching.controller;

import com.caching.model.Book;
import com.caching.service.BookService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @GetMapping("/{id}")
    Book findById(@PathVariable Long id) {
        return service.findBookById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    @GetMapping
    List<Book> findAll() {
        return service.findAllBooks();
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable @NotNull Long id) {
        service.deleteBook(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Book save(@RequestBody @NotNull Book book) {
        return service.saveBook(book);
    }

    @PutMapping("/{id}")
    Book update(@PathVariable Long id, @RequestBody @NotNull Book book) {
        return service.updateBook(id, book);
    }
}
