package com.wisam.eventsmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wisam.eventsmanager.entities.Presenter;

import java.util.List;

@Repository
public interface PresenterRepository extends JpaRepository<Presenter, Long> {
    List<Presenter> findByPublisherId(Long publisherId);
}
