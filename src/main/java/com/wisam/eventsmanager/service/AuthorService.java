package com.wisam.eventsmanager.service;

import com.wisam.eventsmanager.repository.PublisherRepository;
import com.wisam.eventsmanager.entities.Author;
import com.wisam.eventsmanager.entities.Publisher;
import com.wisam.eventsmanager.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PublisherService publisherService;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, PublisherService publisherService) {
        this.authorRepository = authorRepository;
        this.publisherService = publisherService;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author createAuthor(Author author) {
        Optional<Publisher> publisher = publisherService.getPublisherById(author.getPublisher().getId());
        if (publisher.isPresent()) {
            author.setPublisher(publisher.get());
            return authorRepository.save(author);
        }
        return null;
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isPresent()) {
            Author author = existingAuthor.get();
            author.setName(updatedAuthor.getName());
            author.setProfession(updatedAuthor.getProfession());
            author.setPublisher(updatedAuthor.getPublisher());
            return authorRepository.save(author);
        }
        return null;
    }

    public boolean deleteAuthor(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Author> getAllAuthorsByPublisher(Long publisherId) {
        return authorRepository.findByPublisherId(publisherId);
    }
}
