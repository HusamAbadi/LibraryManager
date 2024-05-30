package com.husam.librarymanager.entities;

import jakarta.persistence.*;
import lombok.Data;
//import javax.persistence.*;

@Data
@Entity
@Table(name = "renters")
public class Renter {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
