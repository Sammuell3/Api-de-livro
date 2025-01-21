package com.example.konkin.service;

import com.example.konkin.model.Book;
import com.example.konkin.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BooksService {

    private final BooksRepository booksRepository;

    public List<Book> getAllBooks(){
        return booksRepository.findAll();
    }

    public Book getBookById(UUID id){
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public Book save(Book book) {
        return booksRepository.save(book);
    }

    public long countBooks() {
        return booksRepository.count();
    }

    public Page<Book> getBooksPage(int page, int size) {
        return booksRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public boolean deleteById(UUID id) {
        if (booksRepository.existsById(id)) {
            booksRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Book> searchDinamicRoutes(String title, String author, Book.BookGenre genre, Book.BookStatus status, String publishYear) {
        return booksRepository.findAll().stream()
            .filter(book -> (title == null || book.getTitle().contains(title)) &&
                            (author == null || book.getAuthor().contains(author)) &&
                            (genre == null || book.getGenre() == genre) &&
                            (status == null || book.getStatus() == status) &&
                            (publishYear == null || book.getPublishYear().equals(publishYear)))
            .collect(Collectors.toList());
    }
}
