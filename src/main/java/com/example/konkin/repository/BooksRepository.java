package com.example.konkin.repository;

import com.example.konkin.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
@Repository
public interface BooksRepository extends JpaRepository<Book, UUID> {
    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR b.title LIKE %:title%) AND " +
            "(:author IS NULL OR b.author LIKE %:author%) AND " +
            "(:genre IS NULL OR b.genre = :genre) AND " +
            "(:status IS NULL OR b.status = :status) AND " +
            "(:publishYear IS NULL OR b.publishYear LIKE %:publishYear%)")
    List<Book> searchDinamicRoutes(
            @Param("title") String title,
            @Param("author") String author,
            @Param("genre") Book.BookGenre genre,
            @Param("status") Book.BookStatus status,
            @Param("publishYear") String publishYear);


    //Book findByTitle(String title);
    //List<Book> findByAuthor(String author);
    //List<Book> findByGenre(Book.BookGenre genre);
    //List<Book> findByStatus(Book.BookStatus status);
}
