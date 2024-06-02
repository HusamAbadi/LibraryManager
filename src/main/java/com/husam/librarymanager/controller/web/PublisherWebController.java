package com.husam.librarymanager.controller.web;

import com.husam.librarymanager.service.PublisherService;
import com.husam.librarymanager.entities.Book;
import com.husam.librarymanager.entities.Publisher;
import com.husam.librarymanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/publishers")
public class PublisherWebController {
    private final PublisherService publisherService;
    private final BookService eventService;

    @Autowired
    public PublisherWebController(PublisherService publisherService, BookService eventService) {
        this.publisherService = publisherService;
        this.eventService = eventService;
    }

    @GetMapping
    public String getAllPublishers(Model model) {
        List<Publisher> publishers = publisherService.getAllPublishers();
        model.addAttribute("publishers", publishers);
        model.addAttribute("publisherForm", new Publisher());
        return "publishers";
    }

    @PostMapping
    public String createPublisher(@ModelAttribute("publisherForm") Publisher publisher) {
        publisherService.createPublisher(publisher);
        return "redirect:/publishers/";
    }

    @GetMapping("/{id}")
    public String getPublisherById(@PathVariable Long id, Model model) {
        Optional<Publisher> publisher = publisherService.getPublisherById(id);
        publisher.ifPresent(value -> {
            model.addAttribute("publisher", value);
            List<Book> books = eventService.getBooksByPublisherId(id);
            model.addAttribute("books", books);
        });
        return publisher.isPresent() ? "publisher-details" : "error";
    }

    @GetMapping("/{id}/books")
    public String getBooksByPublisherId(@PathVariable Long id, Model model) {
        List<Book> books = eventService.getBooksByPublisherId(id);
        model.addAttribute("books", books);
        return "publisher-books";
    }

    @GetMapping("/{id}/delete")
    public String deletePublisher(@PathVariable Long id, Model model) {
        publisherService.deletePublisher(id);
        return "redirect:/publishers";
    }
}
