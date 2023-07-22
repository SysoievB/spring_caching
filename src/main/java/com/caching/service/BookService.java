package com.caching.service;

import com.caching.exception.BookNotFoundException;
import com.caching.model.Book;
import com.caching.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepo repo;

    public Optional<Book> findBookById(Long id) {
        return repo.findById(id);
    }

    public List<Book> findAllBooks() {
        return repo.findAll();
    }

    public Book saveBook(Book book) {
        return repo.save(book)
                .orElseThrow(BookNotFoundException::new);
    }

    public Book updateBook(Book newBook) {
        val oldBook = repo.findById(newBook.getId())
                .orElseThrow(BookNotFoundException::new);

        val updatedBook = oldBook.toBuilder()
                .name(newBook.getName())
                .author(newBook.getAuthor())
                .publishDate(newBook.getPublishDate())
                .description(newBook.getDescription())
                .build();

        return repo.save(updatedBook)
                .orElseThrow(BookNotFoundException::new);

    }

    public void deleteBook(Long id) {
        if (id == null) throw new IllegalArgumentException("Book id cannot be null.");

        if (findBookById(id).isPresent()) {
            repo.deleteById(id);
        } else {
            throw new BookNotFoundException();
        }
    }
}
