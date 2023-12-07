package de.shopitech.mywish.views.geschenke.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.concurrent.ThreadLocalRandom;

public class GeschenklisteCreateCard extends ListItem {

    public GeschenklisteCreateCard() {
        addClassNames(LumoUtility.Background.PRIMARY, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.AlignItems.START, LumoUtility.Padding.MEDIUM,
                LumoUtility.BorderRadius.LARGE, "event-overiew-dummy-card");

        Div div = new Div();
        div.addClassNames(LumoUtility.Background.BASE, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.CENTER,
                LumoUtility.Margin.Bottom.MEDIUM, LumoUtility.Overflow.HIDDEN, LumoUtility.BorderRadius.MEDIUM, LumoUtility.Width.FULL);
        div.setHeight("160px");
        setMinHeight("350px");

        Icon createIcon = new Icon(VaadinIcon.LIST);
        div.add(createIcon);

        Span header = new Span();
        header.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.FontWeight.SEMIBOLD);
        header.setText("Neue Geschenkliste Erstellen");

        Span subtitle = new Span();
        subtitle.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY);
        subtitle.setText("Erstelle eine");


        Button createGeschenkliste = new Button("Create");
        createGeschenkliste.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        Button authorizeUsers = new Button("Authorize User");
        authorizeUsers.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        HorizontalLayout btnGrp = new HorizontalLayout(createGeschenkliste, authorizeUsers);

        add(div, header, subtitle, btnGrp);
    }
}
