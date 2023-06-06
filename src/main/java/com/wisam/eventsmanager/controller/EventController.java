package com.wisam.eventsmanager.controller;

import com.wisam.eventsmanager.domain.Event;
import com.wisam.eventsmanager.domain.Organizer;
import com.wisam.eventsmanager.domain.Presenter;
import com.wisam.eventsmanager.service.EventService;
import com.wisam.eventsmanager.service.OrganizerService;
import com.wisam.eventsmanager.service.PresenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private final OrganizerService organizerService;
    private final PresenterService presenterService;

    @Autowired
    public EventController(EventService eventService, OrganizerService organizerService, PresenterService presenterService) {
        this.eventService = eventService;
        this.organizerService = organizerService;
        this.presenterService = presenterService;
    }

    @GetMapping
    public String getAllEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
        List<Organizer> organizers = organizerService.getAllOrganizers();
        List<Presenter> presenters = presenterService.getAllPresenters();
        model.addAttribute("events", events);
        model.addAttribute("organizers", organizers);
        model.addAttribute("presenters", presenters);
        model.addAttribute("event", new Event()); // Add this line to create a new Event object for the form
        return "events3";
    }

    @PostMapping
    public String createEvent(@ModelAttribute("event") Event event) {
        // Set the organizer and presenter for the event
        Organizer organizer = organizerService.getOrganizerById(event.getOrganizer().getId()).orElse(null);
        Presenter presenter = presenterService.getPresenterById(event.getPresenter().getId()).orElse(null);
        if (organizer != null && presenter != null) {
            event.setOrganizer(organizer);
            event.setPresenter(presenter);
            Event createdEvent = eventService.createEvent(event);
            if (createdEvent != null) {
                return "redirect:/api/events";
            }
        }
        return "error"; // handle error case
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        boolean deleted = eventService.deleteEvent(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
