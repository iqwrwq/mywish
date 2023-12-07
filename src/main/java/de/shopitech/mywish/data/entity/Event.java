package de.shopitech.mywish.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID eventID;

    @ManyToOne
    private Benutzer erstellerUser;

    private String pflichtname;
    private LocalDate datum;
    private String beschreibung;
    private String ort;
    private String adresse;
}
