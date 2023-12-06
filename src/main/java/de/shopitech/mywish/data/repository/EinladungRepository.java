package de.shopitech.mywish.data.repository;

import de.shopitech.mywish.data.entity.Einladung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface EinladungRepository extends JpaRepository<Einladung, UUID> {}
