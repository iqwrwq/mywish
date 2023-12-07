package de.shopitech.mywish.views.geschenke;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import de.shopitech.mywish.data.entity.Benutzer;
import de.shopitech.mywish.data.entity.Event;
import de.shopitech.mywish.data.entity.Geschenk;
import de.shopitech.mywish.data.entity.Geschenkliste;
import de.shopitech.mywish.data.repository.BenutzerRepository;
import de.shopitech.mywish.data.repository.EventRepository;
import de.shopitech.mywish.data.repository.GeschenkRepository;
import de.shopitech.mywish.data.repository.GeschenklisteRepository;
import de.shopitech.mywish.security.AuthenticatedUser;
import de.shopitech.mywish.views.MainLayout;
import de.shopitech.mywish.views.events.EventOverview;
import de.shopitech.mywish.views.geschenke.components.GeschenkCard;
import de.shopitech.mywish.views.geschenke.components.GeschenklisteCard;
import jakarta.annotation.security.PermitAll;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Route(value = "gescenkliste-detail", layout = MainLayout.class)
@PermitAll
public class GeschenklisteDetail extends Main implements HasUrlParameter<String> {

    private final BenutzerRepository benutzerRepository;
    private final GeschenklisteRepository geschenklisteRepository;
    private final GeschenkRepository geschenkRepository;
    private Event eventScope;
    private Geschenkliste geschenklisteScope;
    private Benutzer currentUser;

    public GeschenklisteDetail(BenutzerRepository benutzerRepository, AuthenticatedUser currentUser, GeschenklisteRepository geschenklisteRepository, GeschenkRepository geschenkRepository) {
        this.benutzerRepository = benutzerRepository;
        this.geschenklisteRepository = geschenklisteRepository;
        this.geschenkRepository = geschenkRepository;
        this.currentUser = currentUser.get().get();

        MenuBar menu = new MenuBar();
        MenuItem createEventItem = menu.addItem("Back");
        createEventItem.addClickListener(event -> {
            getUI().ifPresent(ui -> {
                ui.navigate(EventOverview.class);
            });
        });
        Header header = new Header(menu);
        header.addClassName("header");

        add(header);
    }

    private void build() {

        VerticalLayout geschenkeListe = new VerticalLayout();
        geschenkeListe.addClassName("geschenk-liste");
        List<Geschenk> geschenkListe = geschenkRepository.findAll();
        for (Geschenk geschenk : geschenkListe) {
            geschenkeListe.add(new GeschenkCard(geschenk));
        }

        geschenkRepository.findAll();

        Button button = new Button("ceate dummy geschenk");
        button.addClickListener(buttonClickEvent -> {
            Geschenk geschenk = new Geschenk();
            geschenk.setGeschenkID(UUID.randomUUID());

            double randomPreis = ThreadLocalRandom.current().nextDouble(10.0, 100.0);
            int randomMenge = ThreadLocalRandom.current().nextInt(1, 10);

            geschenk.setPreis(randomPreis);
            geschenk.setMenge(randomMenge);

            geschenk.setListe(geschenklisteScope);
            String randomProduktLink = "https://example.com/product/" + UUID.randomUUID().toString();
            geschenk.setProduktLink(randomProduktLink);
            List<String> userEmails = Arrays.asList("sample@email.com", "Arthur.schimpf@gmx.de");
            String randomUserEmail = userEmails.get(ThreadLocalRandom.current().nextInt(userEmails.size()));
            Benutzer reserviererUser = benutzerRepository.findBenutzerByEmail(randomUserEmail).orElse(null);
            geschenk.setReserviererUser(reserviererUser);


            geschenkRepository.save(geschenk);
        });

        add(geschenkeListe, button);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String geschenklisteId) {
        geschenklisteScope = geschenklisteRepository.findById(UUID.fromString(geschenklisteId)).get();

        build();
    }

}
