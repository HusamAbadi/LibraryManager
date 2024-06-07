package com.husam.librarymanager.controller.rest;

import com.husam.librarymanager.service.PublisherService;
import com.husam.librarymanager.entities.Author;
import com.husam.librarymanager.entities.Publisher;
import com.husam.librarymanager.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {
    private final AuthorService authorService;
    private final PublisherService publisherService;

    @Autowired
    public AuthorRestController(AuthorService authorService, PublisherService publisherService) {
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<Author> getAllAuthors(@RequestParam(required = false) Long publisherId) {
        if (publisherId != null) {
            return authorService.getAllAuthorsByPublisher(publisherId);
        } else {
            return authorService.getAllAuthors();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@ModelAttribute("authorForm") Author author) {

        Long publisherId = author.getPublisher().getId();
        Optional<Publisher> publisher = publisherService.getPublisherById(publisherId);
        if (publisher.isPresent()) {
            author.setPublisher(publisher.get());
            Author createdAuthor = authorService.createAuthor(author);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        boolean deleted = authorService.deleteAuthor(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
