package de.shopitech.mywish.views.demoData;

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
import de.shopitech.mywish.data.entity.Event;
import de.shopitech.mywish.data.repository.BenutzerRepository;
import de.shopitech.mywish.data.repository.EventRepository;
import de.shopitech.mywish.security.AuthenticatedUser;
import de.shopitech.mywish.views.events.EventOverview;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Route(value = "create-demo-users")
@PermitAll
public class CreateUsers extends Div implements HasUrlParameter<String> {

    private BenutzerRepository benutzerRepository;
    private Lorem lorem;
    private Random random;

    @Value("${admin.username}")
    private String adminUsername;

    public CreateUsers(BenutzerRepository benutzerRepository) {
        this.benutzerRepository = benutzerRepository;
        this.lorem = LoremIpsum.getInstance();
        this.random = new Random();
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

                int randomPictureIndex = random.nextInt(3) + 1;
                byte[] profilePictureData = loadProfilePictureData(randomPictureIndex);
                benutzer.setProfilePicture(profilePictureData);

                int randomBannerIndex = random.nextInt(5) + 1;
                byte[] bannerData = loadProfilePictureData(randomBannerIndex);
                benutzer.setProfilePicture(bannerData);

                data.add(benutzer);

                benutzerRepository.save(benutzer);
            }

            items.setItems(data);
            add(new H1("☑️ " + parameter + " Event(s) ☑️"));
            add(items);
        } catch (Exception e) {
            add(new Span(e.toString()));
        }
    }

    private byte[] loadProfilePictureData(int index) throws IOException {
        Resource resource = new ClassPathResource("pb" + index + ".jpeg");
        return StreamUtils.copyToByteArray(resource.getInputStream());
    }

    private byte[] loadBannerData(int index) throws IOException {
        Resource resource = new ClassPathResource("banner" + index + ".jpeg");
        return StreamUtils.copyToByteArray(resource.getInputStream());
    }
}
