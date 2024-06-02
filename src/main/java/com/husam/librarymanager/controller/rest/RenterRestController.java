package com.husam.librarymanager.controller.rest;

import com.husam.librarymanager.entities.Renter;
import com.husam.librarymanager.service.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/renters")
public class RenterRestController {
    private final RenterService renterService;

    @Autowired
    public RenterRestController(RenterService renterService) {
        this.renterService = renterService;
    }

    @GetMapping
    public List<Renter> getAllRenters() {
        return renterService.getAllRenters();
    }

    @PostMapping
    public ResponseEntity<Renter> createRenter(@ModelAttribute("renterForm") Renter renter) {

        Renter createdRenter = renterService.createRenter(renter);
        return new ResponseEntity<>(createdRenter, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Renter> getRenterById(@PathVariable Long id) {
        Optional<Renter> renter = renterService.getRenterById(id);
        return renter.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Renter> updateRenter(@PathVariable Long id, @RequestBody Renter renter) {
        Renter updatedRenter = renterService.updateRenter(id, renter);
        return updatedRenter != null ? new ResponseEntity<>(updatedRenter, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRenter(@PathVariable Long id) {
        boolean deleted = renterService.deleteRenter(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
