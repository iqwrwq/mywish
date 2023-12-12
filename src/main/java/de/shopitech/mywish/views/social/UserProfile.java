package de.shopitech.mywish.views.social;

import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import de.shopitech.mywish.data.entity.Benutzer;
import de.shopitech.mywish.data.repository.BenutzerRepository;
import de.shopitech.mywish.security.AuthenticatedUser;
import de.shopitech.mywish.views.MainLayout;
import jakarta.annotation.security.PermitAll;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Route(value = "profile", layout = MainLayout.class)
@PermitAll
public class UserProfile extends VerticalLayout implements HasUrlParameter<String> {

    private Benutzer scope;
    private Image profilePicture;
    private BenutzerRepository benutzerRepository;

    public UserProfile(AuthenticatedUser authenticatedUser, BenutzerRepository benutzerRepository) {
        this.scope = authenticatedUser.get().get();
        this.benutzerRepository = benutzerRepository;

        Image banner = new Image();
        StreamResource resourceBanner = new StreamResource("profile-banner", () -> new ByteArrayInputStream(scope.getProfileBanner()));
        banner.setSrc(resourceBanner);
        banner.addClassName("profile-banner");

        this.profilePicture = new Image();
        profilePicture.addClassName("profile-picture");
        StreamResource resourcePicture = new StreamResource("profile-picture", () -> new ByteArrayInputStream(scope.getProfilePicture()));
        profilePicture.setSrc(resourcePicture);

        Header header = new Header(banner, profilePicture);
        header.addClassName("profile-header");

        Span name = new Span(scope.getVorname() + " " + scope.getNachname());
        Paragraph sub = new Paragraph(scope.getUsername());

        VerticalLayout nameAndSub = new VerticalLayout(name, sub);

        Span eventsCount = new Span("69");
        Span eventsLabel = new Span("Events");
        Div events = new Div(eventsCount, eventsLabel);

        Span followerCount = new Span("420");

        Span followsCount = new Span("7");

        HorizontalLayout profileStats = new HorizontalLayout(eventsCount, followerCount, followsCount);
        profileStats.addClassName("profile-stats");

        HorizontalLayout profileRow = new HorizontalLayout(nameAndSub, profileStats);
        profileRow.addClassName("profile-row");

        add(header, profileRow);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {

        if (!(parameter == null)) {
            this.scope = benutzerRepository.findById(UUID.fromString(parameter)).get();

            if (scope.getProfilePicture() != null) {
                StreamResource resourcePicture = new StreamResource("profile-picture", () -> new ByteArrayInputStream(scope.getProfilePicture()));
                profilePicture.setSrc(resourcePicture);
            }

            if (scope.getProfileBanner() != null) {
                StreamResource resourceBanner = new StreamResource("profile-banner", () -> new ByteArrayInputStream(scope.getProfileBanner()));
                profilePicture.setSrc(resourceBanner);
            }
        }
    }
}
