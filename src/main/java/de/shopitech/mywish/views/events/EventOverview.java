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
import de.shopitech.mywish.views.MainLayout;
import de.shopitech.mywish.views.events.components.EventCard;
import jakarta.annotation.security.PermitAll;

@PageTitle("Shopitech MyWish Event Übersicht")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class EventOverview extends Main {

    public EventOverview() {

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

        main.add(new EventCard("1", "https://picsum.photos/400/200"));
        main.add(new EventCard("2", "https://picsum.photos/401/200"));
        main.add(new EventCard("3", "https://picsum.photos/402/200"));
        main.add(new EventCard("4", "https://picsum.photos/403/200"));
        main.add(new EventCard("5", "https://picsum.photos/404/200"));
        main.add(new EventCard("6", "https://picsum.photos/405/200"));
        main.add(new EventCard("7", "https://picsum.photos/406/200"));
        main.add(new EventCard("8", "https://picsum.photos/407/200"));
        main.add(new EventCard("9", "https://picsum.photos/408/200"));
        main.add(new EventCard("10", "https://picsum.photos/409/200"));
        main.add(new EventCard("11", "https://picsum.photos/410/200"));

        add(header, main);
    }
}
