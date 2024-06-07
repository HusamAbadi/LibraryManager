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
    private final RenterService renterService;
    private final BookService bookService;

    @Autowired
    public RenterWebController(RenterService renterService, BookService bookService) {
        this.renterService = renterService;
        this.bookService = bookService;
    }

    @GetMapping
    public String getAllRenters(Model model) {
        List<Renter> renters = renterService.getAllRenters();
        List<Book> books = bookService.getAllBooks();

        model.addAttribute("renters", renters);
        model.addAttribute("books", books);

        return "renters";
    }
    @PostMapping
    public String createRenter(@ModelAttribute("renter") Renter renter, @RequestParam("bookId") Long bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
        if (book.isPresent()) {
            renter.setBook(book.get());
            renterService.createRenter(renter);
            return "redirect:/renters";
        } else {
            return "error";
        }
    }

    @GetMapping("/{id}")
    public String getRenterById(@PathVariable Long id, Model model) {
        Optional<Renter> renter = renterService.getRenterById(id);
        renter.ifPresent(value -> model.addAttribute("renter", value));
        return renter.isPresent() ? "renter-details" : "error";
    }

    @PutMapping("/{id}")
    public String updateRenter(@PathVariable Long id, @ModelAttribute("renter") Renter renter) {
        Renter updatedRenter = renterService.updateRenter(id, renter);
        return updatedRenter != null ? "redirect:/renters" : "error";
    }

    @GetMapping("/{id}/delete")
    public String deleteRenter(@PathVariable Long id) {
        renterService.deleteRenter(id);
        return "redirect:/renters";
    }
}
