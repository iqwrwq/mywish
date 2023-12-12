package de.shopitech.mywish.views.demoData;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.flow.component.UI;
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
import de.shopitech.mywish.data.entity.Geschenk;
import de.shopitech.mywish.data.repository.BenutzerRepository;
import de.shopitech.mywish.data.repository.EventRepository;
import de.shopitech.mywish.data.repository.GeschenkRepository;
import de.shopitech.mywish.views.events.CreateEvent;
import de.shopitech.mywish.views.events.EventOverview;
import jakarta.annotation.security.PermitAll;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Route(value = "create-demo-events")
@PermitAll
public class CreateEvents extends Div implements HasUrlParameter<String> {

    private BenutzerRepository benutzerRepository;
    private EventRepository eventRepository;
    private Lorem lorem;
    private Random random;

    public CreateEvents(BenutzerRepository benutzerRepository, EventRepository eventRepository) {
        this.benutzerRepository = benutzerRepository;
        this.eventRepository = eventRepository;
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
            long count = eventRepository.count();
            eventRepository.deleteAll();
            add(new H1("☑️ " + count + " deleted Event(s) ☑️"));

            return;
        }

        try {
            Grid<Event> items = new Grid<>(Event.class);
            List<Event> data = new ArrayList<>();
            List<Benutzer> allUsers = benutzerRepository.findAll();

            for (int i = 0; i < Integer.parseInt(parameter); i++) {
                Event event = new Event();
                event.setEventID(UUID.randomUUID());
                event.setAdresse(lorem.getCity());
                event.setBeschreibung(lorem.getWords(5));
                int randomPictureIndex = random.nextInt(5) + 1;
                byte[] banner = loadEventBanner(randomPictureIndex);
                event.setEventBanner(banner);
                LocalDate currentDate = LocalDate.now();
                int randomDays = new Random().nextInt(365) + 1;
                LocalDate futureDate = currentDate.plusDays(randomDays);
                event.setDatum(futureDate);
                event.setOrt(lorem.getCity());
                event.setPflichtname(lorem.getName());
                int randomUserIndex = random.nextInt(allUsers.size());
                Benutzer randomUser = allUsers.get(randomUserIndex);
                event.setErstellerUser(randomUser);

                data.add(event);

                eventRepository.save(event);
            }

            items.setItems(data);
            add(new H1("☑️ " + parameter + " Event(s) ☑️"));
            add(items);
        } catch (Exception e) {
            add(new Span(e.toString()));
        }
    }

    private byte[] loadEventBanner(int index) throws IOException {
        Resource resource = new ClassPathResource("eventBanner" + index + ".jpeg");
        return StreamUtils.copyToByteArray(resource.getInputStream());
    }
}