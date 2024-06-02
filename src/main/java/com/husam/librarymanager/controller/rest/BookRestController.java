package com.husam.librarymanager.controller.rest;

import com.husam.librarymanager.entities.Book;
import com.husam.librarymanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookRestController {
    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String createBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        Book createdBook = bookService.createBook(book);
        if (createdBook != null) {
            redirectAttributes.addFlashAttribute("book", createdBook);
            return "<button type=\"button\" class=\"main-button\" onclick=\"location.href='/books'\">Back to books</button>"; // Redirect
                                                                                                                              // to
                                                                                                                              // the
                                                                                                                              // books
                                                                                                                              // list
                                                                                                                              // page
        } else {
            // Handle the case when book creation fails
            return "error";
        }
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book,
            RedirectAttributes redirectAttributes) {
        Book updatedBook = bookService.updateBook(id, book);
        if (updatedBook != null) {
            redirectAttributes.addFlashAttribute("book", updatedBook);
            return "redirect:/books/" + id; // Redirect to the updated book details page
        } else {
            // Handle the case when the book is not found
            return "error";
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
