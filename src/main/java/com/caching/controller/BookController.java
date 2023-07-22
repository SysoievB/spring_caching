package com.caching.controller;

import com.caching.model.Book;
import com.caching.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
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
}
