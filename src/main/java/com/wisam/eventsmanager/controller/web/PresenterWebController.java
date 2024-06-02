package com.wisam.eventsmanager.controller.web;

import com.wisam.eventsmanager.service.PublisherService;
import com.wisam.eventsmanager.entities.Presenter;
import com.wisam.eventsmanager.entities.Publisher;
import com.wisam.eventsmanager.service.PresenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/presenters")
public class PresenterWebController {
    private final PresenterService presenterService;
    private final PublisherService publisherService;

    @Autowired
    public PresenterWebController(PresenterService presenterService, PublisherService publisherService) {
        this.presenterService = presenterService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public String getPresenters(Model model) {
        List<Presenter> presenters = presenterService.getAllPresenters();
        List<Publisher> publishers = publisherService.getAllPublishers();
        model.addAttribute("presenters", presenters);
        model.addAttribute("publishers", publishers);
        model.addAttribute("presenter", new Presenter());
        return "presenters";
    }

    @PostMapping
    public String createPresenter(@ModelAttribute("presenter") Presenter presenter) {
        // Set the publisher for the presenter
        Publisher publisher = publisherService.getPublisherById(presenter.getPublisher().getId()).orElse(null);
        if (publisher != null) {
            presenter.setPublisher(publisher);
            presenterService.createPresenter(presenter);
        }
        return "redirect:/presenters";
    }

    @GetMapping("/{id}")
    public String getPresenterById(@PathVariable Long id, Model model) {
        presenterService.getPresenterById(id).ifPresent(presenter -> model.addAttribute("presenter", presenter));
        return "presenter-details";
    }

    @GetMapping("/{id}/delete")
    public String deletePresenter(@PathVariable Long id) {
        presenterService.deletePresenter(id);
        return "redirect:/presenters";
    }
}
