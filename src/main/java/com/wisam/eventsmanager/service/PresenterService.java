package com.wisam.eventsmanager.service;

import com.wisam.eventsmanager.repository.PublisherRepository;
import com.wisam.eventsmanager.entities.Presenter;
import com.wisam.eventsmanager.entities.Publisher;
import com.wisam.eventsmanager.repository.PresenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PresenterService {
    private final PresenterRepository presenterRepository;
    private final PublisherService publisherService;

    @Autowired
    public PresenterService(PresenterRepository presenterRepository, PublisherService publisherService) {
        this.presenterRepository = presenterRepository;
        this.publisherService = publisherService;
    }

    public List<Presenter> getAllPresenters() {
        return presenterRepository.findAll();
    }

    public Optional<Presenter> getPresenterById(Long id) {
        return presenterRepository.findById(id);
    }

    public Presenter createPresenter(Presenter presenter) {
        Optional<Publisher> publisher = publisherService.getPublisherById(presenter.getPublisher().getId());
        if (publisher.isPresent()) {
            presenter.setPublisher(publisher.get());
            return presenterRepository.save(presenter);
        }
        return null;
    }

    public Presenter updatePresenter(Long id, Presenter updatedPresenter) {
        Optional<Presenter> existingPresenter = presenterRepository.findById(id);
        if (existingPresenter.isPresent()) {
            Presenter presenter = existingPresenter.get();
            presenter.setName(updatedPresenter.getName());
            presenter.setExpertise(updatedPresenter.getExpertise());
            presenter.setPublisher(updatedPresenter.getPublisher());
            return presenterRepository.save(presenter);
        }
        return null;
    }

    public boolean deletePresenter(Long id) {
        Optional<Presenter> presenter = presenterRepository.findById(id);
        if (presenter.isPresent()) {
            presenterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Presenter> getAllPresentersByPublisher(Long publisherId) {
        return presenterRepository.findByPublisherId(publisherId);
    }
}
