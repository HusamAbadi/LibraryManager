package com.wisam.librarymanager.controller.rest;

import com.wisam.librarymanager.entities.Renter;
import com.wisam.librarymanager.service.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/renters")
public class RenterRestController {
    private final RenterService attendeeService;

    @Autowired
    public RenterRestController(RenterService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @GetMapping
    public List<Renter> getAllAttendees() {
        return attendeeService.getAllAttendees();
    }

    @PostMapping
    public ResponseEntity<Renter> createAttendee(@ModelAttribute("attendeeForm") Renter renter) {

        Renter createdAttendee = attendeeService.createAttendee(renter);
        return new ResponseEntity<>(createdAttendee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Renter> getAttendeeById(@PathVariable Long id) {
        Optional<Renter> renter = attendeeService.getAttendeeById(id);
        return renter.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Renter> updateAttendee(@PathVariable Long id, @RequestBody Renter renter) {
        Renter updatedAttendee = attendeeService.updateAttendee(id, renter);
        return updatedAttendee != null ? new ResponseEntity<>(updatedAttendee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendee(@PathVariable Long id) {
        boolean deleted = attendeeService.deleteAttendee(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
