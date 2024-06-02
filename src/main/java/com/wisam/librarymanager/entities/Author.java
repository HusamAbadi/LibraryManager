package com.wisam.librarymanager.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String profession;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
}
