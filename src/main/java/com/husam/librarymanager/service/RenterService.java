package com.husam.librarymanager.service;

import org.springframework.stereotype.Service;

import com.husam.librarymanager.entities.Renter;
import com.husam.librarymanager.repository.RenterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RenterService {
    private final RenterRepository renterRepository;

    public RenterService(RenterRepository renterRepository) {
        this.renterRepository = renterRepository;
    }

    public List<Renter> getAllRenters() {
        return renterRepository.findAll();
    }

    public Optional<Renter> getRenterById(Long id) {
        return renterRepository.findById(id);
    }

    public Renter createRenter(Renter renter) {
        return renterRepository.save(renter);
    }

    public Renter updateRenter(Long id, Renter updatedRenter) {
        Optional<Renter> existingRenter = renterRepository.findById(id);
        if (existingRenter.isPresent()) {
            Renter renter = existingRenter.get();
            renter.setName(updatedRenter.getName());
            renter.setEmail(updatedRenter.getEmail());
            return renterRepository.save(renter);
        }
        return null;
    }

    public boolean deleteRenter(Long id) {
        Optional<Renter> renter = renterRepository.findById(id);
        if (renter.isPresent()) {
            renterRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
