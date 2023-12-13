package de.shopitech.mywish.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.shopitech.mywish.data.entity.Benutzer;
import de.shopitech.mywish.data.repository.BenutzerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static de.shopitech.mywish.data.entity.Benutzer.DICE_API_URL;

@Service
public class DefaultAdminService {

    private final BenutzerRepository benutzerRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${unsplash.accesskey}")
    public String UNSPLASH_ACCESS_KEY;

    @Value("${unsplash.secret}")
    public String UNSPLASH_SECRET_KEY;

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
            adminUser.setVorname("ADMIN");
            adminUser.setNachname("ADMIN");
            adminUser.setAvatarUrl(DICE_API_URL + adminUser.getUserID());
            adminUser.setBannerUrl(getRandomUnsplashImageUrl());

            benutzerRepository.save(adminUser);

            System.out.println("Default admin user created successfully.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }

    private String getRandomUnsplashImageUrl() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.unsplash.com/photos/random?client_id=" + UNSPLASH_ACCESS_KEY))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());

            return jsonNode.get("urls").get("regular").asText();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
