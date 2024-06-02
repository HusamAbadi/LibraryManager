package com.wisam.librarymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wisam.librarymanager.entities.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
