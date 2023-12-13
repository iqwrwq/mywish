package de.shopitech.mywish.views.demoData;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import de.shopitech.mywish.data.entity.Benutzer;
import de.shopitech.mywish.data.repository.BenutzerRepository;
import de.shopitech.mywish.security.AuthenticatedUser;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.shopitech.mywish.data.entity.Benutzer.DICE_API_URL;

@Route(value = "create-demo-users")
@PermitAll
public class CreateDemoBenutzer extends Div implements HasUrlParameter<String> {

    private final BenutzerRepository benutzerRepository;
    private final Lorem lorem;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${unsplash.accesskey}")
    public String UNSPLASH_ACCESS_KEY;

    @Value("${unsplash.secret}")
    public String UNSPLASH_SECRET_KEY;

    public CreateDemoBenutzer(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
        this.lorem = LoremIpsum.getInstance();
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        Button back = new Button("Back");
        back.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(DeveloperDashboard.class);
            });
        });
        add(back);

        if (parameter.equals("deleteall")) {
            benutzerRepository.deleteAllByEmailNot(adminUsername);

            long count = benutzerRepository.count();
            add(new H1("☑️ " + count + " left User(s) ☑️"));

            return;
        }

        Grid<Benutzer> items = new Grid<>(Benutzer.class);
        List<Benutzer> data = new ArrayList<>();

        try {
            for (int i = 0; i < Integer.parseInt(parameter); i++) {
                Benutzer benutzer = new Benutzer();
                benutzer.setUserID(UUID.randomUUID());
                benutzer.setEmail(lorem.getEmail());
                benutzer.setUsername(lorem.getName());
                benutzer.setVorname(lorem.getFirstName());
                benutzer.setNachname(lorem.getLastName());
                benutzer.setEncryptedPassword(AuthenticatedUser.hashPassword("123"));
                benutzer.setAvatarUrl(DICE_API_URL + benutzer.getUserID().toString());
                benutzer.setBannerUrl(getRandomUnsplashImageUrl());

                data.add(benutzer);

                benutzerRepository.save(benutzer);
            }

            items.setItems(data);
            add(new H1("☑️ " + parameter + " Event(s) ☑️"));
            add(items);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
