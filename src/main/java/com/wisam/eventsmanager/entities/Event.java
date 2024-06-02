package com.wisam.eventsmanager.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private int maxAttendees;

    @OneToMany(mappedBy = "event")
    private List<Attendee> attendees;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "presenter_id")
    private Presenter presenter;
}
