package com.example.konkin.controller;

import com.example.konkin.model.Book;
import com.example.konkin.DTO.BookRequestDTO;
import com.example.konkin.service.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookRequestDTO bookRequestDTO) {

        var book = Book.builder()
                .title(bookRequestDTO.getTitle())
                .imageUrl(bookRequestDTO.getImageUrl())
                .author(bookRequestDTO.getAuthor())
                .description(bookRequestDTO.getDescription())
                .publisher(bookRequestDTO.getPublisher())
                .edition(bookRequestDTO.getEdition())
                .genre(bookRequestDTO.getGenre() != null ? Book.BookGenre.valueOf(bookRequestDTO.getGenre().toUpperCase()) : null)
                .language(bookRequestDTO.getLanguage() != null ? bookRequestDTO.getLanguage() : "")
                .status(bookRequestDTO.getStatus() != null ? Book.BookStatus.valueOf(bookRequestDTO.getStatus().toUpperCase()) : Book.BookStatus.DISPONIVEL)
                .publishYear(bookRequestDTO.getPublishYear())
                .isbn(bookRequestDTO.getIsbn())
                .build();

        // Salvar o livro no banco de dados
        var savedBook = booksService.save((Book) book);

        //return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }


    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        var books = booksService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/filters")
    public ResponseEntity<List<Book>> getBooksByVariable(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String publishYear
    ) {
        try {
            // Log dos parâmetros recebidos
            System.out.println("Parâmetros recebidos: title=" + title + ", author=" + author + ", genre=" + genre + ", status=" + status + ", publishYear=" + publishYear);

            // Converte os parâmetros de String para os tipos de enum
            Book.BookGenre bookGenre = genre != null ? Book.BookGenre.valueOf(genre.toUpperCase()) : null;
            Book.BookStatus bookStatus = status != null ? Book.BookStatus.valueOf(status.toUpperCase()) : null;

            // Chama o serviço de busca com os parâmetros fornecidos
            List<Book> books = booksService.searchDinamicRoutes(title, author, bookGenre, bookStatus, publishYear);

            // Retorna uma resposta com status HTTP 200 (OK) e a lista de livros encontrados
            return ResponseEntity.ok(books);

        } catch (IllegalArgumentException e) {
            // Captura erros de conversão de enums ou outros parâmetros inválidos
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid parameter: " + e.getMessage(), e);
        } catch (Exception e) {
            // Log do erro para diagnóstico
            System.err.println("Erro ao buscar livros: " + e.getMessage());
            // Captura quaisquer outros erros
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching books.", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        var book = booksService.getBookById(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id){
        boolean isDeleted = booksService.deleteById(id);
        if (isDeleted){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countBook(){
        long booksCount = booksService.countBooks();
        Map<String, Long> response = new HashMap<>();
        response.put("totalBooks: ", booksCount);
        return ResponseEntity.ok(response);
    }

}
