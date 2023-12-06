package de.shopitech.mywish.data.repository;

import de.shopitech.mywish.data.entity.Geschenk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface GeschenkRepository extends JpaRepository<Geschenk, UUID> {}
