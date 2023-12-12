package de.shopitech.mywish.data.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Geschenkliste {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID listeID;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Benutzer erstellerUser;

    private String name;

    @ManyToOne
    private Benutzer berechtigungUser;
}
