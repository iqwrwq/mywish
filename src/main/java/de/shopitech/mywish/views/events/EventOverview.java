package de.shopitech.mywish.views.events;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import de.shopitech.mywish.data.entity.Event;
import de.shopitech.mywish.data.repository.EventRepository;
import de.shopitech.mywish.security.AuthenticatedUser;
import de.shopitech.mywish.views.MainLayout;
import de.shopitech.mywish.views.events.components.EventCard;
import jakarta.annotation.security.PermitAll;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Event Übersicht")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class EventOverview extends Main {

    private EventRepository eventRepository;

    public EventOverview(EventRepository eventRepository, AuthenticatedUser authenticatedUser) {
        this.eventRepository = eventRepository;

        MenuBar menu = new MenuBar();
        MenuItem createEventItem = menu.addItem("Create");
        createEventItem.addClickListener(event-> {
            getUI().ifPresent(ui -> {
                ui.navigate(CreateEvent.class);
            });
        });
        Header header = new Header(menu);
        header.addClassName("header");

        Main main = new Main();

        main.addClassName("event-overview");

        List<Event> eventList = eventRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Event::getDatum))
                .toList();
        for (Event event : eventList) {
            main.add(new EventCard(event, authenticatedUser));
        }

        add(header, main);
    }
}
