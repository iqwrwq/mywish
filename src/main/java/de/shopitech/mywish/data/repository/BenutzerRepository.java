package de.shopitech.mywish.data.repository;

import de.shopitech.mywish.data.entity.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface BenutzerRepository extends JpaRepository<Benutzer, UUID> {

    @Transactional
    Optional<Benutzer> findBenutzerByEmail(String email);

    @Transactional
    void deleteAllByEmailNot(String email);
}

