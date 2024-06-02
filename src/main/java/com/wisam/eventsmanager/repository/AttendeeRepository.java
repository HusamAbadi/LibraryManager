package com.wisam.eventsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wisam.eventsmanager.entities.Attendee;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
}
