package com.wisam.eventsmanager.controller.web;

import com.wisam.eventsmanager.entities.Event;
import com.wisam.eventsmanager.entities.Presenter;
import com.wisam.eventsmanager.entities.Publisher;
import com.wisam.eventsmanager.service.EventService;
import com.wisam.eventsmanager.service.PublisherService;
import com.wisam.eventsmanager.service.PresenterService;
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
@RequestMapping("/events")
public class EventWebController {
    private final EventService eventService;
    private final PublisherService publisherService;
    private final PresenterService presenterService;

    @Autowired
    public EventWebController(EventService eventService, PublisherService publisherService,
            PresenterService presenterService) {
        this.eventService = eventService;
        this.publisherService = publisherService;
        this.presenterService = presenterService;
    }

    @GetMapping
    public String getAllEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
        List<Publisher> publishers = publisherService.getAllPublishers();
        List<Presenter> presenters = presenterService.getAllPresenters();
        model.addAttribute("events", events);
        model.addAttribute("publishers", publishers);
        model.addAttribute("presenters", presenters);
        model.addAttribute("event", new Event()); // Add this line to create a new Event object for the form
        return "events3";
    }

    @GetMapping("/{id}")
    public String showEventDetails(@PathVariable("id") Long id, Model model) {
        Optional<Event> event = eventService.getEventById(id);
        if (event.isPresent()) {
            model.addAttribute("event", event.get());
            return "event-details";
        } else {
            // Handle the case when the event is not found
            return "error";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditEventForm(@PathVariable("id") Long id, Model model) {
        Optional<Event> event = eventService.getEventById(id);
        if (event.isPresent()) {
            model.addAttribute("event", event.get());
            List<Publisher> publishers = publisherService.getAllPublishers();
            model.addAttribute("publishers", publishers);
            return "event-update";
        } else {
            // Handle the case when the event is not found
            return "error";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteEvent(@PathVariable Long id, Model model) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam("maxAttendees") int maxAttendees,
            @RequestParam("publisherId") Long publisherId,
            @RequestParam("presenterId") Long presenterId) {
        // Create the Event object with the provided parameters
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setDate(date);
        event.setMaxAttendees(maxAttendees);

        // Set the publisher and presenter for the event
        Publisher publisher = publisherService.getPublisherById(publisherId).orElse(null);
        Presenter presenter = presenterService.getPresenterById(presenterId).orElse(null);
        if (publisher != null && presenter != null) {
            event.setPublisher(publisher);
            event.setPresenter(presenter);
            Event createdEvent = eventService.createEvent(event);
            if (createdEvent != null) {
                return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
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
