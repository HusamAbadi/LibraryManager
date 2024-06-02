package com.wisam.librarymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wisam.librarymanager.entities.Renter;

@Repository
public interface AttendeeRepository extends JpaRepository<Renter, Long> {
}
