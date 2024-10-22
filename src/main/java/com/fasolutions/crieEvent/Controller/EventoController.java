package com.fasolutions.crieEvent.Controller;

import com.fasolutions.crieEvent.dto.request.EventoDTO.EventoIdDTO;
import com.fasolutions.crieEvent.dto.request.EventoDTO.EventoRequestDTO;
import com.fasolutions.crieEvent.dto.request.PariticpanteDTO.ParticipanteRequestDTO;
import com.fasolutions.crieEvent.dto.response.EventoDTO.EventoResponseDTO;
import com.fasolutions.crieEvent.dto.response.ParticipanteDTO.ParticipanteIdDTO;
import com.fasolutions.crieEvent.dto.response.ParticipanteDTO.ParticipanteListaResponseDTO;
import com.fasolutions.crieEvent.service.EventoService;
import com.fasolutions.crieEvent.service.ParticipanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/evento")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;
    private final ParticipanteService participanteService;
    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoResponseDTO> getEvento(@PathVariable String idEvento){
        EventoResponseDTO evento = this.eventoService.getEventoDetalhes(idEvento);
        return ResponseEntity.ok(evento);
    }
    @PostMapping
    public ResponseEntity<EventoIdDTO> criarEvento(@RequestBody EventoRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventoIdDTO criaEvento = this.eventoService.criarEvento(body);

        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(criaEvento.idEvento()).toUri();

        return ResponseEntity.created(uri).body(criaEvento);
    }

    @GetMapping("/participante/{idEvento}")
    public ResponseEntity<ParticipanteListaResponseDTO> getEventoParticipantes(@PathVariable String idEvento){
        ParticipanteListaResponseDTO listaPariticipantes = this.participanteService.getEventosParticipante(idEvento);

        return ResponseEntity.ok(listaPariticipantes);
    }

    /** Rota para criar o crachá do participante **/
    @PostMapping("{eventoId}/participante")
    public ResponseEntity<ParticipanteIdDTO> criarEvento(@PathVariable String eventoId, @RequestBody ParticipanteRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        ParticipanteIdDTO criaCracha = this.eventoService.registraParticipanteNoEvento(eventoId, body);

        var uri = uriComponentsBuilder.path("/participante/{participanteId}/cracha").buildAndExpand(criaCracha.participanteid()).toUri();

        return ResponseEntity.created(uri).body(criaCracha);
    }

}
