package com.wisam.librarymanager.service;

import com.wisam.librarymanager.entities.Renter;
import com.wisam.librarymanager.repository.AttendeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RenterService {
    private final AttendeeRepository attendeeRepository;

    @Autowired
    public RenterService(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    public List<Renter> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    public Optional<Renter> getAttendeeById(Long id) {
        return attendeeRepository.findById(id);
    }

    public Renter createAttendee(Renter renter) {
        return attendeeRepository.save(renter);
    }

    public Renter updateAttendee(Long id, Renter updatedAttendee) {
        Optional<Renter> existingAttendee = attendeeRepository.findById(id);
        if (existingAttendee.isPresent()) {
            Renter renter = existingAttendee.get();
            renter.setName(updatedAttendee.getName());
            renter.setEmail(updatedAttendee.getEmail());
            return attendeeRepository.save(renter);
        }
        return null;
    }

    public boolean deleteAttendee(Long id) {
        Optional<Renter> renter = attendeeRepository.findById(id);
        if (renter.isPresent()) {
            attendeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
