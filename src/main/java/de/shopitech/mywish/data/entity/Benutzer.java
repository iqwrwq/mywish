package de.shopitech.mywish.data.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userID;

    private String email;
    private String vorname;
    private String nachname;
    private String username;
    private String encryptedPassword;
    private Date geburtsdatum;

    // Getter und Setter
}
