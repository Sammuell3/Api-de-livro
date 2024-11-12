package com.example.konkin.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "book")
public class Book {

    public enum BookStatus {
        DISPONIVEL("Disponível"),
        SEM_ESTOQUE("Sem Estoque"),
        PRE_VENDA("Pré Venda");

        private final String statusDescription;
        BookStatus(String statusDescription) {
            this.statusDescription = statusDescription;
        }

        public String getStatusDescription() {
            return statusDescription;
        }
    }

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

        public String getGenreDescription() {
            return genreDescription;
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
}