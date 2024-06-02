package com.wisam.librarymanager.service;

import com.wisam.librarymanager.entities.Book;
import com.wisam.librarymanager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository eventRepository;

    @Autowired
    public BookService(BookRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Book> getAllBooks() {
        return eventRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return eventRepository.findById(id);
    }

    public Book createBook(Book book) {
        return eventRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Optional<Book> existingBook = eventRepository.findById(id);
        if (existingBook.isPresent()) {
            updatedBook.setId(id);
            return eventRepository.save(updatedBook);
        }
        return null;
    }

    public boolean deleteBook(Long id) {
        Optional<Book> book = eventRepository.findById(id);
        if (book.isPresent()) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Book> getBooksByPublisherId(Long publisherId) {
        return eventRepository.findByPublisherId(publisherId);
    }

}
