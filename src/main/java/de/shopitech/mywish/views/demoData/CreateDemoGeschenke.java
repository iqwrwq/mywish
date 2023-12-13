package de.shopitech.mywish.views.demoData;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import de.shopitech.mywish.data.entity.Geschenk;
import de.shopitech.mywish.data.repository.GeschenkRepository;
import jakarta.annotation.security.PermitAll;

import java.util.*;

@Route(value = "create-demo-geschenke")
@PermitAll
public class CreateDemoGeschenke extends Div implements HasUrlParameter<String> {

    private GeschenkRepository geschenkRepository;
    private Lorem lorem;
    private Random random;

    public CreateDemoGeschenke(GeschenkRepository geschenkRepository) {
        this.geschenkRepository = geschenkRepository;
        this.lorem = LoremIpsum.getInstance();
        this.random = new Random();
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        Button back = new Button("Back");
        back.addClickListener(buttonClickEvent -> {
            getUI().ifPresent(ui -> {
                ui.navigate(DeveloperDashboard.class);
            });
        });
        add(back);

        if (parameter.equals("deleteall")) {
            long count = geschenkRepository.count();
            geschenkRepository.deleteAll();
            add(new H1("☑️ " + count + " deleted Geschenk(e) ☑️"));

            return;
        }

        Grid<Geschenk> items = new Grid<>(Geschenk.class);
        List<Geschenk> data = new ArrayList<>();

        try {
            for (int i = 0; i < Integer.parseInt(parameter); i++) {
                Geschenk geschenk = new Geschenk();
                geschenk.setGeschenkID(UUID.randomUUID());
                geschenk.setMenge(random.nextInt(20) + 1);
                geschenk.setName(lorem.getName());
                geschenk.setProduktLink(lorem.getUrl());
                double randomNumber = (random.nextDouble() * 998) + 1;
                randomNumber = Math.round(randomNumber * 100.0) / 100.0;
                geschenk.setPreis(randomNumber);

                data.add(geschenk);

                geschenkRepository.save(geschenk);
            }

            items.setItems(data);
            add(new H1("☑️ " + parameter + " Geschenk(e) ☑️"));
            add(items);
        } catch (Exception e) {
            add(new Span(e.toString()));
        }
    }
}
