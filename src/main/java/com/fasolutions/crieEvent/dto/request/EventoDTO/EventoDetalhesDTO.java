package com.fasolutions.crieEvent.dto.request.EventoDTO;

public record EventoDetalhesDTO(
        String id,
        String nome,
        String detalhes,
        String slug,
        Integer qtdMaxima,
        Integer somaParticipantes) {
}
