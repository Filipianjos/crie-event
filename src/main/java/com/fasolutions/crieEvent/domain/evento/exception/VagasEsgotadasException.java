package com.fasolutions.crieEvent.domain.evento.exception;

public class VagasEsgotadasException extends RuntimeException{
    public VagasEsgotadasException(String mensagem){
        super(mensagem);
    }
}
