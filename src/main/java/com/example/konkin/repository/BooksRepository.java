package com.example.konkin.repository;

import com.example.konkin.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
@Repository
public interface BooksRepository extends JpaRepository<Book, UUID> {
    Book findByName(String name);
    List<Book> findByAuthor(String author);
    List<Book> findByGenre(Book.BookGenre genre);
    List<Book> findByStatus(Book.BookStatus status);
}
