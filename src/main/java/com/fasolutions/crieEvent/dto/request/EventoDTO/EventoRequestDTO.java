package com.fasolutions.crieEvent.dto.request.EventoDTO;

public record EventoRequestDTO(
        String nome,
        String detalhes,
        Integer qtdMaxima
){}
