package de.shopitech.mywish.data.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Einladung {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID einladungID;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Benutzer eingeladenerUser;

    private String einladungsStatus;
}
