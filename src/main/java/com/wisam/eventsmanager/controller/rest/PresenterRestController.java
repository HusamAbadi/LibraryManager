package com.wisam.eventsmanager.controller.rest;

import com.wisam.eventsmanager.service.PublisherService;
import com.wisam.eventsmanager.entities.Presenter;
import com.wisam.eventsmanager.entities.Publisher;
import com.wisam.eventsmanager.service.PresenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/presenters")
public class PresenterRestController {
    private final PresenterService presenterService;
    private final PublisherService publisherService;

    @Autowired
    public PresenterRestController(PresenterService presenterService, PublisherService publisherService) {
        this.presenterService = presenterService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<Presenter> getAllPresenters(@RequestParam(required = false) Long publisherId) {
        if (publisherId != null) {
            return presenterService.getAllPresentersByPublisher(publisherId);
        } else {
            return presenterService.getAllPresenters();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Presenter> getPresenter(@PathVariable Long id) {
        Optional<Presenter> presenter = presenterService.getPresenterById(id);
        return presenter.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Presenter> createPresenter(@ModelAttribute("presenterForm") Presenter presenter) {

        Long publisherId = presenter.getPublisher().getId();
        Optional<Publisher> publisher = publisherService.getPublisherById(publisherId);
        if (publisher.isPresent()) {
            presenter.setPublisher(publisher.get());
            Presenter createdPresenter = presenterService.createPresenter(presenter);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPresenter);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePresenter(@PathVariable Long id) {
        boolean deleted = presenterService.deletePresenter(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
