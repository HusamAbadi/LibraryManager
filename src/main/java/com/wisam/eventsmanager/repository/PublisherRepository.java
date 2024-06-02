package com.wisam.eventsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wisam.eventsmanager.entities.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
