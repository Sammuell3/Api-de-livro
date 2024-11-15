package com.example.konkin.service;

import com.example.konkin.model.Book;
import com.example.konkin.repository.BooksRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BooksService {

    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository){
        this.booksRepository = booksRepository;
    }

    public List<Book> getAllBooks(){
        return booksRepository.findAll();
    }

    public Book getNameBook( String name){
        return booksRepository.findByName(name);
    }

    public Book getBookById(UUID id){
        return booksRepository.findById(id).orElse(null);
    }

    public List<Book> getBookByAuthor(String author){
        return booksRepository.findByAuthor(author);
    }

    public Book save(Book book) {
        return booksRepository.save(book);
    }

    public long countBooks() {
        return booksRepository.count();
    }

    public boolean existById(UUID id){
        return booksRepository.existsById(id);
    }

    public List<Book> getBooksByGere(Book.BookGenre bookGere) {
        return booksRepository.findByGenre(bookGere);
    }

    public List<Book> getBooksByStatus(Book.BookStatus status) {
       return booksRepository.findByStatus(status);
    }

    public Page<Book> getBooksPage(int page, int size) {
        return booksRepository.findAll(PageRequest.of(page, size));
    }

    public void deleteById(UUID id) {
        booksRepository.deleteById(id);
    }

}
