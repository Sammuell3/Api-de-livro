package com.example.konkin.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;


@Entity
@Data
@Table(name = "book")
public class Book {


    @Getter
    public enum BookStatus {
        DISPONIVEL("Disponível"),
        SEM_ESTOQUE("Sem Estoque"),
        PRE_VENDA("Pré Venda");

        private final String statusDescription;
        BookStatus(String statusDescription) {
            this.statusDescription = statusDescription;
        }

    }

    @Getter
    public enum BookGenre {
        FILOSOFIA("Filosofia"),
        ECONOMIA("Economia"),
        POLITICA("Política"),
        HISTORIA("História"),
        CIENCIA_SOCIAL("Ciência Social"),
        FICCAO("Ficção"),
        BIOGRAFIA("Biografia"),
        EDUCACAO("Educação"),
        PSICOLOGIA("Psicologia"),
        SOCIOLOGIA("Sociologia"),
        ANTROPOLOGIA("Antropologia"),
        OUTROS("Outros");

        private final String genreDescription;
        BookGenre(String genreDescription){
            this.genreDescription = genreDescription;
        }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="ID")
    private UUID id;

    @Column(name="TITLE", nullable = false)
    private String title;

    @Column(name="IMAGE_URL", length = 500)
    private String imageUrl;

    @Column(name="AUTHOR", length = 100)
    private String author;

    @Column(name="DESCRIPTION", length = 1000)
    private String description;

    @Column(name="PUBLISHER", length = 100)
    private String publisher;

    @Column(name="EDITION", length = 50)
    private String edition;

    @Column(name="GENRE")
    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    @Column(name="LANGUAGE", length= 50)
    private String language;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Column(name="PUBLISHER_YEAR", length = 4)
    private String publishYear;

    @Column(name="CREATED_AT")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name="UPDATED_AT")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Column(name="ISBN", unique = true, length = 20)
    private String isbn;

    // Construtor padrão
    public Book() {
    }

    public static BookBuilder builder() {
        return new BookBuilder();
    }

    public static class BookBuilder {
        private String title;
        private String imageUrl;
        private String author;
        private String description;
        private String publisher;
        private String edition;
        private BookGenre genre;
        private String language;
        private BookStatus status;
        private String publishYear;
        private String isbn;

        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder description(String description) {
            this.description = description;
            return this;
        }

        public BookBuilder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookBuilder edition(String edition) {
            this.edition = edition;
            return this;
        }

        public BookBuilder genre(BookGenre genre) {
            this.genre = genre;
            return this;
        }

        public BookBuilder language(String language) {
           this.language = language;
            return this;
        }

        public BookBuilder status(BookStatus status) {
            this.status = status;
            return this;
        }

        public BookBuilder publishYear(String publishYear) {
            this.publishYear = publishYear;
            return this;
        }

        public BookBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Book build() {
            Book book = new Book();
            book.title = this.title;
            book.imageUrl = this.imageUrl;
            book.author = this.author;
            book.description = this.description;
            book.publisher = this.publisher;
            book.edition = this.edition;
            book.genre = this.genre;
            book.language = this.language;
            book.status = this.status;
            book.publishYear = this.publishYear;
            book.isbn = this.isbn;
            return book;
        }
    }
}