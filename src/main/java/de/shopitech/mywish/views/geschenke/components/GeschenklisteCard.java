package de.shopitech.mywish.views.geschenke.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.shopitech.mywish.data.entity.Benutzer;
import de.shopitech.mywish.data.entity.Geschenkliste;
import de.shopitech.mywish.security.AuthenticatedUser;
import de.shopitech.mywish.views.events.EventDetail;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class GeschenklisteCard extends ListItem {

    private Geschenkliste geschenklisteScope;
    private Benutzer benutzerScope;

    public GeschenklisteCard(Geschenkliste geschenklisteScope, Benutzer currentUser) {
        this.geschenklisteScope = geschenklisteScope;
        this.benutzerScope = geschenklisteScope.getErstellerUser();

        addClassNames(LumoUtility.Background.CONTRAST_5, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.AlignItems.START, LumoUtility.Padding.MEDIUM,
                LumoUtility.BorderRadius.LARGE, "event-overiew-card");

        Div div = new Div();
        div.addClassNames(LumoUtility.Background.CONTRAST, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.CENTER,
                LumoUtility.Margin.Bottom.MEDIUM, LumoUtility.Overflow.HIDDEN, LumoUtility.BorderRadius.MEDIUM, LumoUtility.Width.FULL);
        div.setHeight("160px");
        setMinHeight("350px");

        Image image = new Image();
        image.setWidth("100%");
        String imageUrl = String.format("https://picsum.photos/%d/%d",
                ThreadLocalRandom.current().nextInt(395, 416),
                ThreadLocalRandom.current().nextInt(195, 216));
        image.setSrc(imageUrl);

        div.add( image);

        Span header = new Span();
        header.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.FontWeight.SEMIBOLD);
        header.setText("Geschenkliste");

        Span subtitle = new Span();
        subtitle.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY);
        subtitle.setText("some text");

        Paragraph description = new Paragraph("eventScope.getBeschreibung()");
        description.addClassName(LumoUtility.Margin.Vertical.MEDIUM);

        Span badge = new Span();
        if (currentUser.getEmail().equals(benutzerScope.getEmail())) {
            badge.getElement().setAttribute("theme", "badge");
            badge.setText("Yours");
        } else {
            badge.getElement().setAttribute("theme", "badge contrast");
            badge.setText(benutzerScope.getVorname());
        }

        add(div, header, subtitle, description, badge);
    }
}
