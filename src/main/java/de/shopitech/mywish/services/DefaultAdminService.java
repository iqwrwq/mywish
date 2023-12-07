package de.shopitech.mywish.services;

import de.shopitech.mywish.data.entity.Benutzer;
import de.shopitech.mywish.data.repository.BenutzerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAdminService {

    private final BenutzerRepository benutzerRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    public DefaultAdminService(BenutzerRepository benutzerRepository, PasswordEncoder passwordEncoder) {
        this.benutzerRepository = benutzerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createDefaultAdminUser() {
        Optional<Benutzer> existingAdmin = benutzerRepository.findBenutzerByEmail(adminUsername);

        if (existingAdmin.isEmpty()) {
            Benutzer adminUser = new Benutzer();
            adminUser.setEmail(adminUsername);
            adminUser.setEncryptedPassword(passwordEncoder.encode(adminPassword));
            benutzerRepository.save(adminUser);

            System.out.println("Default admin user created successfully.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
