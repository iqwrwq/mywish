package de.shopitech.mywish.views.events.components;

import com.vaadin.flow.component.avatar.AvatarGroup;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.shopitech.mywish.data.entity.Benutzer;
import de.shopitech.mywish.data.entity.Event;
import de.shopitech.mywish.security.AuthenticatedUser;
import de.shopitech.mywish.views.events.EventDetail;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class EventCard extends ListItem {

    private Event eventScope;
    private Benutzer benutzerScope;

    public EventCard(Event event, AuthenticatedUser currentUser) {
        this.eventScope = event;
        this.benutzerScope = eventScope.getErstellerUser();

        addClassNames(LumoUtility.Background.CONTRAST_5, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.AlignItems.START, LumoUtility.Padding.MEDIUM,
                LumoUtility.BorderRadius.LARGE, "event-overiew-card");
        addClickListener(clickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(EventDetail.class, eventScope.getEventID().toString());
            });
        });

        Div div = new Div();
        div.addClassNames(LumoUtility.Background.CONTRAST, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.CENTER,
                LumoUtility.Margin.Bottom.MEDIUM, LumoUtility.Overflow.HIDDEN, LumoUtility.BorderRadius.MEDIUM, LumoUtility.Width.FULL);
        div.setHeight("160px");

        Image image = new Image();
        image.setWidth("100%");
        String imageUrl = String.format("https://picsum.photos/%d/%d",
                ThreadLocalRandom.current().nextInt(395, 416),
                ThreadLocalRandom.current().nextInt(195, 216));
        image.setSrc(imageUrl);

        div.add(image);

        Span header = new Span();
        header.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.FontWeight.SEMIBOLD);
        header.setText(eventScope.getDatum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " + eventScope.getPflichtname());

        Span subtitle = new Span();
        subtitle.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY);
        subtitle.setText(eventScope.getOrt() + " " + eventScope.getAdresse());

        Paragraph description = new Paragraph(eventScope.getBeschreibung());
        description.addClassName(LumoUtility.Margin.Vertical.MEDIUM);

        AvatarGroup avatarGroup = new AvatarGroup();
        AvatarGroup.AvatarGroupItem[] avatarGroupItems = getRandomAvatarGroupItems();
        avatarGroup.setItems(avatarGroupItems);

        add(div, header, subtitle, description, avatarGroup);
    }

    private AvatarGroup.AvatarGroupItem[] getRandomAvatarGroupItems() {
        Random random = new Random();
        int numberOfItems = random.nextInt(4);

        List<AvatarGroup.AvatarGroupItem> items = new ArrayList<>();

        for (int i = 0; i < numberOfItems; i++) {
            AvatarGroup.AvatarGroupItem item = new AvatarGroup.AvatarGroupItem();
            items.add(item);
        }

        return items.toArray(new AvatarGroup.AvatarGroupItem[0]);
    }
}
