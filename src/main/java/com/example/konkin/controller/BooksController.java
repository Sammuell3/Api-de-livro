package com.example.konkin.controller;

import com.example.konkin.model.Book;
import com.example.konkin.request.BookRequest;
import com.example.konkin.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookRequest bookRequest) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setImageUrl(bookRequest.getImageUrl());
        book.setAuthor(bookRequest.getAuthor());
        book.setDescription(bookRequest.getDescription());
        book.setPublisher(bookRequest.getPublisher());
        book.setEdition(bookRequest.getEdition());
        book.setGenre(Book.BookGenre.valueOf(bookRequest.getGenre().toUpperCase()));  // Convertendo string para enum
        book.setLanguage(bookRequest.getLanguage());
        book.setStatus(Book.BookStatus.valueOf(bookRequest.getStatus().toUpperCase()));  // Convertendo string para enum
        book.setPublishYear(bookRequest.getPublishYear());
        book.setIsbn(bookRequest.getIsbn());

        // Salvar o livro no banco de dados
        Book savedBook = booksService.save(book);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = booksService.getAllBooks();
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
        Book.BookStatus bookStatus = null;
        if (status != null) {
            try {
                bookStatus = Book.BookStatus.valueOf(status); // Converte a String para o enum
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Retorna erro se o status não for válido
            }
        }

        Book.BookGenre bookGenre = null; // Mover a declaração para fora do bloco
        if (genre != null) {
            try {
                bookGenre = Book.BookGenre.valueOf(genre); // Converte a String para o enum
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Retorna erro se o gênero não for válido
            }
        }

        List<Book> filter = booksService.Search(title, author, bookGenre, bookStatus, publishYear);
        if (filter.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(filter, HttpStatus.OK);
    }

/*
    @GetMapping("/title/{title}")
    public ResponseEntity<Book> getTitleBook(@PathVariable String title) {
        Book book = booksService.getTitleBook(title);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        Book book = booksService.getBookById(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
/*
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Book>> getBooksByStatus(@PathVariable Book.BookStatus status) {
        List<Book> books = booksService.getBooksByStatus(status);
        if (!books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookByid(@PathVariable UUID id){
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
