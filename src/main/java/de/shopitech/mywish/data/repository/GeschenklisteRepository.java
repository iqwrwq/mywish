package de.shopitech.mywish.data.repository;

import de.shopitech.mywish.data.entity.Geschenkliste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface GeschenklisteRepository extends JpaRepository<Geschenkliste, UUID> {}
