package de.shopitech.mywish.data.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID eventID;

    @ManyToOne
    private Benutzer erstellerUser;

    private String pflichtname;
    private Date datum;
    private String beschreibung;
    private String ort;
    private String adresse;

    // Getter und Setter
}
