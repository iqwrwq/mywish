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

    private Benutzer currentUser;
    private Benutzer scope;
    private BenutzerRepository benutzerRepository;

    public UserProfile(AuthenticatedUser authenticatedUser, BenutzerRepository benutzerRepository) {
        this.currentUser = authenticatedUser.get().get();
        this.benutzerRepository = benutzerRepository;

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        if ((parameter == null)) {
            this.scope = currentUser;

        } else {
            this.scope = benutzerRepository.findById(UUID.fromString(parameter)).get();
        }

        Image banner = new Image();
        banner.setSrc(scope.getBannerUrl());
        banner.addClassName("profile-banner");

        Image profilePicture = new Image();
        profilePicture.addClassName("profile-picture");
        profilePicture.setSrc(scope.getAvatarUrl());

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
}
