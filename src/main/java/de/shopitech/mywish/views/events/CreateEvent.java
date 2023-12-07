package de.shopitech.mywish.views.events;



import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.avatar.AvatarGroup.AvatarGroupItem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import de.shopitech.mywish.components.PriceField;
import de.shopitech.mywish.data.entity.Benutzer;
import de.shopitech.mywish.data.entity.Event;
import de.shopitech.mywish.data.repository.BenutzerRepository;
import de.shopitech.mywish.data.repository.EventRepository;
import de.shopitech.mywish.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@PageTitle("Shopitech MyWish Event Erstellen")
@Route(value = "create-event", layout = MainLayout.class)
@PermitAll
public class CreateEvent extends Composite<VerticalLayout> {

    private final BenutzerRepository benutzerRepository;
    private final EventRepository eventRepository;

    public CreateEvent(BenutzerRepository benutzerRepository, EventRepository eventRepository) {
        this.benutzerRepository = benutzerRepository;
        this.eventRepository = eventRepository;

        Button createDummyData = new Button("create dummy data");
        createDummyData.addClickListener(event -> {
            Event event1 = new Event();
            event1.setEventID(UUID.randomUUID());

            // Randomly select one of the users
            List<String> userEmails = Arrays.asList("sample@email.com", "Arthur.schimpf@gmx.de");
            String randomUserEmail = userEmails.get(ThreadLocalRandom.current().nextInt(userEmails.size()));
            event1.setErstellerUser(benutzerRepository.findBenutzerByEmail(randomUserEmail).orElseThrow());

            // Generate a random event name
            event1.setPflichtname(generateRandomString(15));

            // Generate a random date within a specific range
            LocalDate startDate = LocalDate.of(2023, 12, 1);
            LocalDate endDate = LocalDate.of(2023, 12, 31);
            long startEpochDay = startDate.toEpochDay();
            long endEpochDay = endDate.toEpochDay();
            long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
            LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
            event1.setDatum(randomDate);

            // Generate random description, ort, and adresse
            event1.setBeschreibung(generateRandomString(20));
            event1.setOrt(generateRandomString(10));
            event1.setAdresse(generateRandomString(15));

            eventRepository.save(event1);
        });

        VerticalLayout layoutColumn2 = new VerticalLayout();
        layoutColumn2.add(createDummyData);

        VerticalLayout layoutColumn3 = new VerticalLayout();
        AvatarGroup avatarGroup = new AvatarGroup();
        H1 h1 = new H1();
        TextField textField = new TextField();
        FormLayout formLayout2Col = new FormLayout();
        DatePicker datePicker = new DatePicker();
        TimePicker timePicker = new TimePicker();
        TextField textField2 = new TextField();
        PriceField price = new PriceField();
        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        TextArea textArea = new TextArea();
        HorizontalLayout layoutRow = new HorizontalLayout();
        PriceField price2 = new PriceField();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        TextField textField3 = new TextField();
        TextField textField4 = new TextField();
        NumberField numberField = new NumberField();
        H2 h2 = new H2();
        Grid<Benutzer> basicGrid = new Grid<>(Benutzer.class);
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        buttonSecondary.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(EventOverview.class);
            });
        });
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        avatarGroup.setWidth("min-content");
        setAvatarGroupSampleData(avatarGroup);
        h1.setText("New Event");
        h1.setWidth("max-content");
        textField.setLabel("Event Name");
        textField.setWidth("192px");
        formLayout2Col.setWidth("100%");
        datePicker.setLabel("Date");
        datePicker.setWidth("min-content");
        timePicker.setLabel("Time");
        timePicker.setWidth("min-content");
        textField2.setLabel("Occupation");
        price.setLabel("Price (Optional)");
        price.setWidth("min-content");
        checkboxGroup.setLabel("Optionals");
        checkboxGroup.setWidth("min-content");

        Checkbox setPriceCheckBox = new Checkbox("Price");
        checkboxGroup.setItems(setPriceCheckBox.getLabel());
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        textArea.setLabel("Description");
        textArea.setWidth("100%");
        layoutRow.setHeightFull();
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        price2.setLabel("Price");
        price2.setWidth("min-content");
        layoutRow3.setHeightFull();
        layoutRow3.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.getStyle().set("flex-grow", "1");
        textField3.setLabel("Text field");
        textField3.setWidth("min-content");
        textField4.setLabel("Text field");
        textField4.setWidth("min-content");
        numberField.setLabel("Number field");
        numberField.setWidth("min-content");
        h2.setText("Add Friends");
        h2.setWidth("max-content");
        basicGrid.setWidth("100%");
        basicGrid.getStyle().set("flex-grow", "0");
        setGridSampleData(basicGrid);
        layoutRow2.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        buttonPrimary.setText("Save");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Cancel");
        buttonSecondary.setWidth("min-content");
        getContent().add(layoutColumn2);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(avatarGroup);
        layoutColumn3.add(h1);
        layoutColumn2.add(textField);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(datePicker);
        formLayout2Col.add(timePicker);
        formLayout2Col.add(textField2);
        formLayout2Col.add(price);
        formLayout2Col.add(checkboxGroup);
        formLayout2Col.add(textArea);
        formLayout2Col.add(layoutRow);
        layoutRow.add(price2);
        formLayout2Col.add(layoutRow3);
        formLayout2Col.add(textField3);
        formLayout2Col.add(textField4);
        formLayout2Col.add(numberField);
        getContent().add(h2);
        getContent().add(basicGrid);
        getContent().add(layoutRow2);
        layoutRow2.add(buttonPrimary);
        layoutRow2.add(buttonSecondary);
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

    private void setAvatarGroupSampleData(AvatarGroup avatarGroup) {
    }

    private void setGridSampleData(Grid grid) {
        grid.setItems(benutzerRepository.findAll());
    }

}
