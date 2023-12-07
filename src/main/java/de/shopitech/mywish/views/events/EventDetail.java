package de.shopitech.mywish.views.events;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.*;
import de.shopitech.mywish.data.entity.Benutzer;
import de.shopitech.mywish.data.entity.Event;
import de.shopitech.mywish.data.entity.Geschenkliste;
import de.shopitech.mywish.data.repository.BenutzerRepository;
import de.shopitech.mywish.data.repository.EventRepository;
import de.shopitech.mywish.data.repository.GeschenklisteRepository;
import de.shopitech.mywish.security.AuthenticatedUser;
import de.shopitech.mywish.views.MainLayout;
import de.shopitech.mywish.views.geschenke.GeschenklisteDetail;
import de.shopitech.mywish.views.geschenke.components.GeschenklisteCard;
import de.shopitech.mywish.views.geschenke.components.GeschenklisteCreateCard;
import jakarta.annotation.security.PermitAll;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Route(value = "event-detail", layout = MainLayout.class)
@PermitAll
public class EventDetail extends Main implements HasUrlParameter<String> {


    private final BenutzerRepository benutzerRepository;
    private final EventRepository eventRepository;
    private final GeschenklisteRepository geschenklisteRepository;
    private Benutzer currentUser;
    private Event event;
    private Benutzer benutzerScope;

    public EventDetail(BenutzerRepository benutzerRepository, AuthenticatedUser currentUser, EventRepository eventRepository, GeschenklisteRepository geschenklisteRepository) {
        this.benutzerRepository = benutzerRepository;
        this.currentUser = currentUser.get().get();
        this.eventRepository = eventRepository;
        this.geschenklisteRepository = geschenklisteRepository;

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

    @Override
    public void setParameter(BeforeEvent beforeEvent, String eventId) {
        this.event = eventRepository.findByEventID(UUID.fromString(eventId)).get();
        this.benutzerScope = event.getErstellerUser();

        build();
    }

    private void build() {
        Div eventContainer = new Div();
        eventContainer.addClassName("event-detail-container");

        Image image = new Image();
        image.setWidth("100%");
        String imageUrl = "https://picsum.photos/1400/400";
        image.setSrc(imageUrl);
        image.addClassName("event-detail-image");

        Div titleRow = new Div();
        titleRow.addClassName("event-detail-title-row");

        Span eventName = new Span(event.getPflichtname());
        eventName.addClassName("event-detail-name");

        Span eventDate = new Span("Date: " + event.getDatum().toString());
        eventDate.addClassName("event-detail-name");

        Span eventUserName = new Span("From: " + event.getErstellerUser().getVorname());
        eventUserName.addClassName("event-detail-name");

        Button joinEvent = new Button("Join");
        joinEvent.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button rejectEvent = new Button("Reject");
        rejectEvent.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        Div buttonGroup = new Div(joinEvent, rejectEvent);
        buttonGroup.addClassName("btn-grp");
        titleRow.add(eventName, eventDate, eventUserName, buttonGroup);

        eventContainer.add(image, titleRow);

        VerticalLayout eventBody = new VerticalLayout();
        eventBody.addClassName("event-detail-body");

        AvatarGroup participants = new AvatarGroup();
        participants.setItems(new AvatarGroup.AvatarGroupItem(), new AvatarGroup.AvatarGroupItem(), new AvatarGroup.AvatarGroupItem(), new AvatarGroup.AvatarGroupItem());

        TextField adress = new TextField("Adress");
        adress.setValue(event.getAdresse());
        adress.setWidthFull();
        adress.setReadOnly(true);
        adress.addThemeVariants(
                TextFieldVariant.LUMO_SMALL,
                TextFieldVariant.MATERIAL_ALWAYS_FLOAT_LABEL
        );
        TextField location = new TextField("Location");
        location.setValue(event.getOrt());
        location.setWidthFull();
        location.setReadOnly(true);
        location.addThemeVariants(
                TextFieldVariant.LUMO_SMALL,
                TextFieldVariant.MATERIAL_ALWAYS_FLOAT_LABEL
        );
        TextArea description = new TextArea("Description");
        description.setValue(event.getBeschreibung());
        description.setWidthFull();
        description.setReadOnly(true);
        description.setMinHeight("400px");
        description.addThemeVariants(
                TextAreaVariant.LUMO_SMALL,
                TextAreaVariant.MATERIAL_ALWAYS_FLOAT_LABEL
        );
        VerticalLayout descriptionBox = new VerticalLayout(participants, adress, location, description);
        eventBody.add(descriptionBox);


        VerticalLayout wishListContainer = new VerticalLayout();
        wishListContainer.addClassName("event-detail-body-wishlist-container");


        wishListContainer.add(new GeschenklisteCreateCard());

        List<Geschenkliste> geschenkListe = geschenklisteRepository.findAll();
        for (Geschenkliste gl : geschenkListe) {
            GeschenklisteCard geschenklisteCard = new GeschenklisteCard(gl, currentUser);
            geschenklisteCard.addClickListener(event -> {
                getUI().ifPresent(ui -> ui.navigate(GeschenklisteDetail.class, gl.getListeID().toString()));
            });
            wishListContainer.add(geschenklisteCard);
        }

        eventBody.add(wishListContainer);

        Button button = new Button("ceate dummy wishlist");
        button.addClickListener(buttonClickEvent -> {
            Geschenkliste geschenkliste = new Geschenkliste();
            geschenkliste.setListeID(UUID.randomUUID());

            List<String> userEmails = Arrays.asList("sample@email.com", "Arthur.schimpf@gmx.de");
            String randomUserEmail = userEmails.get(ThreadLocalRandom.current().nextInt(userEmails.size()));
            geschenkliste.setErstellerUser(benutzerRepository.findBenutzerByEmail(randomUserEmail).orElseThrow());

            geschenkliste.setName(generateRandomString(15));
            geschenkliste.setEvent(event);

            geschenklisteRepository.save(geschenkliste);
        });

        add(eventContainer, eventBody, button);
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = ThreadLocalRandom.current().nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }
}
