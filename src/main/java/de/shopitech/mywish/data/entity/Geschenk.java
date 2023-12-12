package de.shopitech.mywish.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
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
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;
}
