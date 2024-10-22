package com.fasolutions.crieEvent.service;

import com.fasolutions.crieEvent.domain.checkIn.CheckIn;
import com.fasolutions.crieEvent.domain.checkIn.exception.CheckInJaRealizadoException;
import com.fasolutions.crieEvent.domain.participante.Participante;
import com.fasolutions.crieEvent.domain.participante.exception.ParticipanteJaResgistradoException;
import com.fasolutions.crieEvent.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;

    /** Método para consultar o checkin **/
    public Optional<CheckIn> getCheckIn(String participanteId){
        return this.checkInRepository.findByParticipante_Id(participanteId);

    }

    /** Método para a realizlçao do checkin **/
    public void checkInParticipante(Participante participante){
        this.verificaCheckIn(participante.getId());
        CheckIn fazerCheckIn = new CheckIn();
        fazerCheckIn.setParticipante(participante);
        fazerCheckIn.setDataCheckin(LocalDateTime.now());
        this.checkInRepository.save(fazerCheckIn);
    }

    /** Método para verificar se o checkin já foi realizado **/
    private void verificaCheckIn(String participanteId) {
        Optional<CheckIn> verificaCheckIn = this.getCheckIn(participanteId);
        if (verificaCheckIn.isPresent()) throw new CheckInJaRealizadoException("CheckIn já realizado");
    }
}
