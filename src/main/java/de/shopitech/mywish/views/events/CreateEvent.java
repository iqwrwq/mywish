package de.shopitech.mywish.views.events;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import de.shopitech.mywish.data.entity.Benutzer;
import de.shopitech.mywish.data.entity.Event;
import de.shopitech.mywish.data.repository.BenutzerRepository;
import de.shopitech.mywish.data.repository.EventRepository;
import de.shopitech.mywish.security.AuthenticatedUser;
import de.shopitech.mywish.views.MainLayout;
import jakarta.annotation.security.PermitAll;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

@PageTitle("Shopitech MyWish Event Erstellen")
@Route(value = "create-event", layout = MainLayout.class)
@PermitAll
public class CreateEvent extends VerticalLayout {

    private final BenutzerRepository benutzerRepository;
    private final EventRepository eventRepository;
    private final TextField eventName = new TextField("Name");
    private final TextArea description = new TextArea("Description");
    private final TextField eventAdress = new TextField("Street");
    private final DatePicker eventDate = new DatePicker("Date");
    private final TextField eventCity = new TextField("City");
    private final MemoryBuffer buffer = new MemoryBuffer();
    private final Upload imageUpload = new Upload(buffer);
    private byte[] uploadedImage;

    public CreateEvent(AuthenticatedUser authenticatedUser, BenutzerRepository benutzerRepository, EventRepository eventRepository) {
        this.benutzerRepository = benutzerRepository;
        this.eventRepository = eventRepository;
        this.uploadedImage = null;


        Div imageContainer = new Div();
        imageUpload.setAcceptedFileTypes(".jpeg", ".jpg");
        imageUpload.setDropAllowed(false);
        imageUpload.setMaxFiles(1);

        imageUpload.setUploadButton(new Button("Upload Image"));
        imageUpload.addSucceededListener(event -> {
            InputStream fileData = buffer.getInputStream();
            uploadedImage = readBytes(fileData);

            StreamResource resource = new StreamResource("uploaded-image", () -> new ByteArrayInputStream(uploadedImage));
            Image uploadedImageView = new Image(resource, "Uploaded Image");
            uploadedImageView.setWidth("600px");
            uploadedImageView.setHeight("300px");
            imageContainer.add(uploadedImageView);
        });

        imageContainer.add(imageUpload);

        Button save = new Button("Save");
        save.addClickListener(buttonClickEvent -> {
            Event event = new Event();
            UUID id = UUID.randomUUID();
            event.setEventID(id);
            event.setPflichtname(eventName.getValue());

            if (uploadedImage != null && uploadedImage.length > 0) {
                event.setEventBanner(uploadedImage);
            } else {
                try {
                    event.setEventBanner(loadEventBanner());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            event.setBeschreibung(description.getValue());
            event.setAdresse(eventAdress.getValue());
            event.setDatum(eventDate.getValue());
            event.setOrt(eventCity.getValue());
            event.setErstellerUser(authenticatedUser.get().get());

            eventRepository.save(event);
            Notification.show("Event Created!");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                getUI().ifPresent(ui -> ui.navigate(EventDetail.class, event.getEventID().toString()));
            }
        });

        FormLayout formLayout = new FormLayout();
        formLayout.add(eventName, imageContainer, description, eventAdress, eventDate, eventCity, save);
        add(formLayout);
    }

    private byte[] readBytes(InputStream inputStream) {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] loadEventBanner() throws IOException {
        Random random = new Random();
        int randomBannerNumber = random.nextInt(5) + 1;
        String fileName = "eventBanner" + randomBannerNumber + ".jpeg";

        Resource resource = new ClassPathResource(fileName);
        return StreamUtils.copyToByteArray(resource.getInputStream());
    }

}
