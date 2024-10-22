package com.fasolutions.crieEvent.dto.response.EventoDTO;

import com.fasolutions.crieEvent.domain.evento.Evento;
import com.fasolutions.crieEvent.dto.request.EventoDTO.EventoDetalhesDTO;
import lombok.Getter;

@Getter
public class EventoResponseDTO {

    EventoDetalhesDTO evento;
    public  EventoResponseDTO(Evento evento, Integer somaParticipantes){
        this.evento = new EventoDetalhesDTO(
                evento.getId(),
                evento.getNome(),
                evento.getDetalhes(),
                evento.getSlug(),
                evento.getQtdMaxima(),
                somaParticipantes
        );
    }

}
