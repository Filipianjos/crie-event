package com.fasolutions.crieEvent.Controller;

import com.fasolutions.crieEvent.dto.response.ParticipanteDTO.ParticipanteCrachaResponseDTO;
import com.fasolutions.crieEvent.service.CheckInService;
import com.fasolutions.crieEvent.service.ParticipanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/participante")
@RequiredArgsConstructor
public class ParticipanteController {
    private final ParticipanteService participanteService;
    private final CheckInService checkInService;

    /** Rota para exibir o crachá do participante **/
    @GetMapping("/{participanteId}/cracha")
    public ResponseEntity<ParticipanteCrachaResponseDTO> getParticipanteCracha(@PathVariable String participanteId, UriComponentsBuilder uriComponentsBuilder){
        ParticipanteCrachaResponseDTO responseDTO = this.participanteService.getParticipanteCracha(participanteId, uriComponentsBuilder);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/{participanteId}/check-in")
    public ResponseEntity fazerCheckIn(@PathVariable String participanteId, UriComponentsBuilder uriComponentsBuilder){
        this.participanteService.checkInParticipante(participanteId);
        var uri = uriComponentsBuilder.path("/participante/{participanteId}/cracha").buildAndExpand(participanteId).toUri();

        return ResponseEntity.created(uri).build();
    }
}
