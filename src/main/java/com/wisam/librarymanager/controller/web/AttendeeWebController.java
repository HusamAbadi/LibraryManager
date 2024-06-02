package com.wisam.librarymanager.controller.web;

import com.wisam.librarymanager.entities.Attendee;
import com.wisam.librarymanager.entities.Book;
import com.wisam.librarymanager.service.AttendeeService;
import com.wisam.librarymanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/attendees")
public class AttendeeWebController {
    private final AttendeeService attendeeService;
    private final BookService eventService;

    @Autowired
    public AttendeeWebController(AttendeeService attendeeService, BookService eventService) {
        this.attendeeService = attendeeService;
        this.eventService = eventService;
    }

    @GetMapping
    public String getAllAttendees(Model model) {
        List<Attendee> attendees = attendeeService.getAllAttendees();
        List<Book> books = eventService.getAllBooks();

        model.addAttribute("attendees", attendees);
        model.addAttribute("books", books);

        return "attendees";
    }

    @PostMapping
    public String createAttendee(@ModelAttribute("attendee") Attendee attendee, @RequestParam("eventId") Long eventId) {
        Optional<Book> book = eventService.getBookById(eventId);
        if (book.isPresent()) {
            attendee.setBook(book.get());
            attendeeService.createAttendee(attendee);
            return "redirect:/attendees";
        } else {
            return "error";
        }
    }

    @GetMapping("/{id}")
    public String getAttendeeById(@PathVariable Long id, Model model) {
        Optional<Attendee> attendee = attendeeService.getAttendeeById(id);
        attendee.ifPresent(value -> model.addAttribute("attendee", value));
        return attendee.isPresent() ? "attendee-details" : "error";
    }

    @PutMapping("/{id}")
    public String updateAttendee(@PathVariable Long id, @ModelAttribute("attendee") Attendee attendee) {
        Attendee updatedAttendee = attendeeService.updateAttendee(id, attendee);
        return updatedAttendee != null ? "redirect:/attendees" : "error";
    }

    @GetMapping("/{id}/delete")
    public String deleteAttendee(@PathVariable Long id) {
        attendeeService.deleteAttendee(id);
        return "redirect:/attendees";
    }
}
