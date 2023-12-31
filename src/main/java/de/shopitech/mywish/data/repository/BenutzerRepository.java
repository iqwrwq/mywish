package de.shopitech.mywish.data.repository;

import de.shopitech.mywish.data.entity.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface BenutzerRepository extends JpaRepository<Benutzer, UUID> {

    Optional<Benutzer> findBenutzerByEmail(String email);

    void deleteAllByEmailNot(String email);
}

