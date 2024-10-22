package com.fasolutions.crieEvent.config;

import com.fasolutions.crieEvent.domain.checkIn.exception.CheckInJaRealizadoException;
import com.fasolutions.crieEvent.domain.evento.exception.EventoNotFoundException;
import com.fasolutions.crieEvent.domain.evento.exception.VagasEsgotadasException;
import com.fasolutions.crieEvent.domain.participante.exception.ParticipanteJaResgistradoException;
import com.fasolutions.crieEvent.domain.participante.exception.ParticipanteNotFoundException;
import com.fasolutions.crieEvent.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(EventoNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventoNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(VagasEsgotadasException.class)
    public ResponseEntity handleVagasEsgotadas(VagasEsgotadasException exception){
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(ParticipanteNotFoundException.class)
    public ResponseEntity handleParticipanteNotFound(ParticipanteNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ParticipanteJaResgistradoException.class)
    public ResponseEntity handleParticipanteJaExiste(ParticipanteJaResgistradoException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInJaRealizadoException.class)
    public ResponseEntity handleCheckInJarealizado(CheckInJaRealizadoException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
