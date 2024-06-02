package com.wisam.eventsmanager.controller.web;

import com.wisam.eventsmanager.service.PublisherService;
import com.wisam.eventsmanager.entities.Event;
import com.wisam.eventsmanager.entities.Publisher;
import com.wisam.eventsmanager.service.EventService;
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
    private final EventService eventService;

    @Autowired
    public PublisherWebController(PublisherService publisherService, EventService eventService) {
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
            List<Event> events = eventService.getEventsByPublisherId(id);
            model.addAttribute("events", events);
        });
        return publisher.isPresent() ? "publisher-details" : "error";
    }

    @GetMapping("/{id}/events")
    public String getEventsByPublisherId(@PathVariable Long id, Model model) {
        List<Event> events = eventService.getEventsByPublisherId(id);
        model.addAttribute("events", events);
        return "publisher-events";
    }

    @GetMapping("/{id}/delete")
    public String deletePublisher(@PathVariable Long id, Model model) {
        publisherService.deletePublisher(id);
        return "redirect:/publishers";
    }
}
