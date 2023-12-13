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
import de.shopitech.mywish.data.entity.Geschenkliste;
import de.shopitech.mywish.data.repository.BenutzerRepository;
import de.shopitech.mywish.data.repository.EventRepository;
import de.shopitech.mywish.data.repository.GeschenklisteRepository;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Route(value = "create-demo-geschenkelisten")
@PermitAll
public class CreateDemoGeschenkeListen extends Div implements HasUrlParameter<String> {

    private BenutzerRepository benutzerRepository;
    private EventRepository eventRepository;
    private GeschenklisteRepository geschenklisteRepository;
    private Lorem lorem;
    private Random random;

    public CreateDemoGeschenkeListen(BenutzerRepository benutzerRepository, EventRepository eventRepository, GeschenklisteRepository geschenklisteRepository) {
        this.benutzerRepository = benutzerRepository;
        this.eventRepository = eventRepository;
        this.geschenklisteRepository = geschenklisteRepository;
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
            long count = geschenklisteRepository.count();
            geschenklisteRepository.deleteAll();
            add(new H1("☑️ " + count + " deleted Geschenkliste(n) ☑️"));

            return;
        }

        Grid<Geschenkliste> items = new Grid<>(Geschenkliste.class);
        List<Geschenkliste> data = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();
        List<Benutzer> allUsers = benutzerRepository.findAll();

        try {
            for (int i = 0; i < Integer.parseInt(parameter); i++) {
                Geschenkliste geschenkliste = new Geschenkliste();
                geschenkliste.setListeID(UUID.randomUUID());
                geschenkliste.setName(lorem.getName());
                int randomEventIndex = random.nextInt(allEvents.size());
                Event randomEvent = allEvents.get(randomEventIndex);
                geschenkliste.setEvent(randomEvent);
                int randomUserIndex = random.nextInt(allUsers.size());
                Benutzer randomUser = allUsers.get(randomUserIndex);
                geschenkliste.setErstellerUser(randomUser);

                data.add(geschenkliste);

                geschenklisteRepository.save(geschenkliste);
            }

            items.setItems(data);
            add(new H1("☑️ " + parameter + " Geschenkliste(n) ☑️"));
            add(items);
        } catch (Exception e) {
            add(new Span(e.toString()));
        }
    }
}
