package com.husam.librarymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.husam.librarymanager.entities.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
