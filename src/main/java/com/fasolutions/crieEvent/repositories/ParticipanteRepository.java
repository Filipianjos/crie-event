package com.fasolutions.crieEvent.repositories;

import com.fasolutions.crieEvent.domain.participante.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipanteRepository extends JpaRepository<Participante, String> {
    List<Participante> findByEvento_id(String idEvento);

    Optional<Participante> findByEvento_idAndEmail(String evendId, String email);
}