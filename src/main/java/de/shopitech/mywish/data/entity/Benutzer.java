package de.shopitech.mywish.data.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Benutzer {

    public static final String DICE_API_URL = "https://api.dicebear.com/7.x/thumbs/svg?seed=";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userID;
    private String email;
    private String vorname;
    private String nachname;
    private String username;
    private String encryptedPassword;
    private Date geburtsdatum;
    private String avatarUrl;
    private String bannerUrl;
}
