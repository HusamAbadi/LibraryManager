package com.wisam.librarymanager.controller.web;

import com.wisam.librarymanager.entities.Book;
import com.wisam.librarymanager.entities.Author;
import com.wisam.librarymanager.entities.Publisher;
import com.wisam.librarymanager.service.BookService;
import com.wisam.librarymanager.service.PublisherService;
import com.wisam.librarymanager.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookWebController {
    private final BookService eventService;
    private final PublisherService publisherService;
    private final AuthorService authorService;

    @Autowired
    public BookWebController(BookService eventService, PublisherService publisherService,
            AuthorService authorService) {
        this.eventService = eventService;
        this.publisherService = publisherService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = eventService.getAllBooks();
        List<Publisher> publishers = publisherService.getAllPublishers();
        List<Author> authors = authorService.getAllAuthors();
        model.addAttribute("books", books);
        model.addAttribute("publishers", publishers);
        model.addAttribute("authors", authors);
        model.addAttribute("book", new Book()); // Add this line to create a new Book object for the form
        return "books3";
    }

    @GetMapping("/{id}")
    public String showBookDetails(@PathVariable("id") Long id, Model model) {
        Optional<Book> book = eventService.getBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "book-details";
        } else {
            // Handle the case when the book is not found
            return "error";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Optional<Book> book = eventService.getBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            List<Publisher> publishers = publisherService.getAllPublishers();
            model.addAttribute("publishers", publishers);
            return "book-update";
        } else {
            // Handle the case when the book is not found
            return "error";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id, Model model) {
        eventService.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping
    public ResponseEntity<Book> createBook(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam("copiesNum") int copiesNum,
            @RequestParam("publisherId") Long publisherId,
            @RequestParam("authorId") Long authorId) {
        // Create the Book object with the provided parameters
        Book book = new Book();
        book.setName(name);
        book.setDescription(description);
        book.setDate(date);
        book.setCopiesNum(copiesNum);

        // Set the publisher and author for the book
        Publisher publisher = publisherService.getPublisherById(publisherId).orElse(null);
        Author author = authorService.getAuthorById(authorId).orElse(null);
        if (publisher != null && author != null) {
            book.setPublisher(publisher);
            book.setAuthor(author);
            Book createdBook = eventService.createBook(book);
            if (createdBook != null) {
                return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
