package com.fasolutions.crieEvent.dto.request.PariticpanteDTO;

import java.time.LocalDateTime;

public record ParticipanteDetalhes(
        String id,
        String nome,
        String email,
        LocalDateTime criadoEm,
        LocalDateTime checkedEm) {
}
