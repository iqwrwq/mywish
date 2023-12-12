package de.shopitech.mywish.data.repository;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.shopitech.mywish.data.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Transactional
    Optional<Event> findByEventID(UUID id);
}
