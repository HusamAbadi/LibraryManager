package com.wisam.eventsmanager.service;

import com.wisam.eventsmanager.entities.Publisher;
import com.wisam.eventsmanager.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Optional<Publisher> getPublisherById(Long id) {
        return publisherRepository.findById(id);
    }

    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher updatePublisher(Long id, Publisher updatedPublisher) {
        Optional<Publisher> existingPublisher = publisherRepository.findById(id);
        if (existingPublisher.isPresent()) {
            Publisher publisher = existingPublisher.get();
            publisher.setName(updatedPublisher.getName());
            publisher.setDescription(updatedPublisher.getDescription());
            return publisherRepository.save(publisher);
        }
        return null;
    }

    public boolean deletePublisher(Long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isPresent()) {
            publisherRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
