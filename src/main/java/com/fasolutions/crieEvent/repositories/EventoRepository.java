package com.fasolutions.crieEvent.repositories;

import com.fasolutions.crieEvent.domain.evento.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, String> {
}
