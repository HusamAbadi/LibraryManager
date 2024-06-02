package com.husam.librarymanager.controller.rest;

import com.husam.librarymanager.entities.Publisher;
import com.husam.librarymanager.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/publishers")
public class PublisherRestController {
    private final PublisherService publisherService;

    @Autowired
    public PublisherRestController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @PostMapping
    // public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher
    // publisher) {
    public ResponseEntity<Publisher> createPublisher(@ModelAttribute("publisherForm") Publisher publisher) {
        Publisher createdPublisher = publisherService.createPublisher(publisher);
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
        Optional<Publisher> publisher = publisherService.getPublisherById(id);
        return publisher.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long id, @RequestBody Publisher publisher) {
        Publisher updatedPublisher = publisherService.updatePublisher(id, publisher);
        return updatedPublisher != null ? new ResponseEntity<>(updatedPublisher, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        boolean deleted = publisherService.deletePublisher(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
