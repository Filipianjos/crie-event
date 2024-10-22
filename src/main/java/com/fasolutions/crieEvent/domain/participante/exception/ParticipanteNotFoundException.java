package com.fasolutions.crieEvent.domain.participante.exception;

public class ParticipanteNotFoundException extends RuntimeException{
    public ParticipanteNotFoundException(String mensagem){
        super(mensagem);
    }
}
