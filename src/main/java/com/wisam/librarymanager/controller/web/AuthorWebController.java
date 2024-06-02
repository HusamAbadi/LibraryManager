package com.husam.librarymanager.controller.web;

import com.husam.librarymanager.service.PublisherService;
import com.husam.librarymanager.entities.Author;
import com.husam.librarymanager.entities.Publisher;
import com.husam.librarymanager.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorWebController {
    private final AuthorService authorService;
    private final PublisherService publisherService;

    @Autowired
    public AuthorWebController(AuthorService authorService, PublisherService publisherService) {
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public String getAuthors(Model model) {
        List<Author> authors = authorService.getAllAuthors();
        List<Publisher> publishers = publisherService.getAllPublishers();
        model.addAttribute("authors", authors);
        model.addAttribute("publishers", publishers);
        model.addAttribute("author", new Author());
        return "authors";
    }

    @PostMapping
    public String createAuthor(@ModelAttribute("author") Author author) {
        // Set the publisher for the author
        Publisher publisher = publisherService.getPublisherById(author.getPublisher().getId()).orElse(null);
        if (publisher != null) {
            author.setPublisher(publisher);
            authorService.createAuthor(author);
        }
        return "redirect:/authors";
    }

    @GetMapping("/{id}")
    public String getAuthorById(@PathVariable Long id, Model model) {
        authorService.getAuthorById(id).ifPresent(author -> model.addAttribute("author", author));
        return "author-details";
    }

    @GetMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
