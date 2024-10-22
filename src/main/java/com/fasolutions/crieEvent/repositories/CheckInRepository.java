package com.fasolutions.crieEvent.repositories;

import com.fasolutions.crieEvent.domain.checkIn.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    Optional<CheckIn> findByParticipante_Id(String participanteID);
}
