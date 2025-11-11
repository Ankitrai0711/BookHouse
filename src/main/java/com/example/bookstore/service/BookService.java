package com.example.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    @Cacheable(value = "books", unless = "#result == null || #result.isEmpty()")
    public List<Book> getAllBook() {
        return bookRepo.findAll();
    }

    @Cacheable(value = "book", key = "#id", unless = "#result == null")
    public Optional<Book> getBookById(long id) {
        return bookRepo.findById(id);
    }

    @CachePut(value = "book", key = "#result.id", unless = "#result == null")
    public Book createBook(Book book) {
        return bookRepo.save(book);
    }

    @CachePut(value = "book", key = "#id", unless = "#result == null")
    public Book updateBook(long id, Book book) {
        return bookRepo.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(book.getTitle());
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setPrice(book.getPrice());
                    existingBook.setStock(book.getStock());
                    return bookRepo.save(existingBook);
                })
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @CacheEvict(value = "book", key = "#id")
    public void deleteBook(Long id) {
        bookRepo.deleteById(id);
    }
}
