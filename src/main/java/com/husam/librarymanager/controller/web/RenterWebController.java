package com.husam.librarymanager.controller.web;

import com.husam.librarymanager.entities.Renter;
import com.husam.librarymanager.entities.Book;
import com.husam.librarymanager.service.RenterService;
import com.husam.librarymanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/renters")
public class RenterWebController {
    private final RenterService attendeeService;
    private final BookService eventService;

    @Autowired
    public RenterWebController(RenterService attendeeService, BookService eventService) {
        this.attendeeService = attendeeService;
        this.eventService = eventService;
    }

    @GetMapping
    public String getAllAttendees(Model model) {
        List<Renter> renters = attendeeService.getAllAttendees();
        List<Book> books = eventService.getAllBooks();

        model.addAttribute("renters", renters);
        model.addAttribute("books", books);

        return "renters";
    }

    @PostMapping
    public String createAttendee(@ModelAttribute("renter") Renter renter, @RequestParam("eventId") Long eventId) {
        Optional<Book> book = eventService.getBookById(eventId);
        if (book.isPresent()) {
            renter.setBook(book.get());
            attendeeService.createAttendee(renter);
            return "redirect:/renters";
        } else {
            return "error";
        }
    }

    @GetMapping("/{id}")
    public String getAttendeeById(@PathVariable Long id, Model model) {
        Optional<Renter> renter = attendeeService.getAttendeeById(id);
        renter.ifPresent(value -> model.addAttribute("renter", value));
        return renter.isPresent() ? "renter-details" : "error";
    }

    @PutMapping("/{id}")
    public String updateAttendee(@PathVariable Long id, @ModelAttribute("renter") Renter renter) {
        Renter updatedAttendee = attendeeService.updateAttendee(id, renter);
        return updatedAttendee != null ? "redirect:/renters" : "error";
    }

    @GetMapping("/{id}/delete")
    public String deleteAttendee(@PathVariable Long id) {
        attendeeService.deleteAttendee(id);
        return "redirect:/renters";
    }
}
