package de.shopitech.mywish.views.demoData;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import de.shopitech.mywish.views.MainLayout;
import de.shopitech.mywish.views.events.EventOverview;
import jakarta.annotation.security.PermitAll;

@Route(value = "developer-dashboard", layout = MainLayout.class)
@PermitAll
public class DeveloperDashboard extends VerticalLayout {

    public DeveloperDashboard() {
        NumberField numberField = new NumberField();
        String number;

        if (!numberField.isEmpty()) {
            number = String.valueOf(Math.round(numberField.getValue()));
        } else {
            number = "1";
        }

        Button back = new Button("Back");
        back.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(EventOverview.class);
            });
        });

        Button clearUsers = new Button("clear all users");
        clearUsers.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(CreateDemoBenutzer.class, "deleteall");
            });
        });
        Button createUsers = new Button("create demo users");
        createUsers.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(CreateDemoBenutzer.class, number);
            });
        });
        HorizontalLayout d1 = new HorizontalLayout(createUsers, clearUsers);

        Button clearGeschenke = new Button("clear all geschenke");
        clearGeschenke.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(CreateDemoGeschenke.class, "deleteall");
            });
        });
        Button createGeschenke = new Button("create demo geschenke");
        createGeschenke.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(CreateDemoGeschenke.class, number);
            });
        });
        HorizontalLayout d2 = new HorizontalLayout(createGeschenke, clearGeschenke);

        Button clearEvents = new Button("clear all events");
        clearEvents.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(CreateDemoEvents.class, "deleteall");
            });
        });
        Button createEvents = new Button("create demo events");
        createEvents.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(CreateDemoEvents.class, number);
            });
        });
        HorizontalLayout d3 = new HorizontalLayout(createEvents, clearEvents);

        Button clearGeschenkeListe = new Button("clear all geschenklisten");
        clearGeschenkeListe.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(CreateDemoGeschenkeListen.class, "deleteall");
            });
        });
        Button createGeschenkeListe = new Button("create demo geschenkeListe");
        createGeschenkeListe.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(CreateDemoGeschenkeListen.class, number);
            });
        });
        HorizontalLayout d4 = new HorizontalLayout(createGeschenkeListe, clearGeschenkeListe);


        add(back, numberField, d1, d2, d3, d4);
    }
}
