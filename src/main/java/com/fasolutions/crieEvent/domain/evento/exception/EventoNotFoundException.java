package com.fasolutions.crieEvent.domain.evento.exception;

public class EventoNotFoundException extends RuntimeException {
    public EventoNotFoundException(String mensagem){
        super(mensagem);
    }
}
