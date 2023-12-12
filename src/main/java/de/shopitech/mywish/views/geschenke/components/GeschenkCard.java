package de.shopitech.mywish.views.geschenke.components;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import de.shopitech.mywish.data.entity.Geschenk;

import java.util.concurrent.ThreadLocalRandom;

public class GeschenkCard extends ListItem {

    private Geschenk geschenkScope;

    public GeschenkCard(Geschenk geschenkScope) {
        this.geschenkScope = geschenkScope;

        addClassNames(LumoUtility.Background.CONTRAST_5, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN, LumoUtility.AlignItems.START, LumoUtility.Padding.MEDIUM,
                LumoUtility.BorderRadius.LARGE, "geschenke-overiew-card");

        HorizontalLayout div = new HorizontalLayout();
        div.addClassNames(LumoUtility.Background.CONTRAST, LumoUtility.Display.FLEX, LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.CENTER,
                LumoUtility.Margin.Bottom.MEDIUM, LumoUtility.Overflow.HIDDEN, LumoUtility.BorderRadius.MEDIUM, LumoUtility.Width.FULL);
        div.setHeight("160px");
        setMinHeight("350px");

        Image banner = new Image();
        banner.setWidth("100%");
        String imageUrl = String.format("https://picsum.photos/%d/%d",
                ThreadLocalRandom.current().nextInt(195, 216),
                ThreadLocalRandom.current().nextInt(195, 216));
        banner.setSrc(imageUrl);

        div.add(banner);

        Span header = new Span();
        header.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.FontWeight.SEMIBOLD);
        header.setText("Geschenk");

        Span subtitle = new Span();
        subtitle.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.TextColor.SECONDARY);
        subtitle.setText("some text");

        add(div, header, subtitle);
    }
}
