package com.fasolutions.crieEvent.dto.response.ParticipanteDTO;

import com.fasolutions.crieEvent.domain.participante.Participante;
import com.fasolutions.crieEvent.dto.request.PariticpanteDTO.ParticipanteDetalhes;
import lombok.Getter;
import java.util.List;

public record ParticipanteListaResponseDTO(List<ParticipanteDetalhes> participantes) {
}
