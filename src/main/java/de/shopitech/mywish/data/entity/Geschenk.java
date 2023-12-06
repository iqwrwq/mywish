package de.shopitech.mywish.data.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Geschenk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID geschenkID;

    @ManyToOne
    private Geschenkliste liste;

    private String name;
    private Double preis;
    private Integer menge;
    private String produktLink;

    @ManyToOne
    private Benutzer reserviererUser;

    // Getter und Setter
}
