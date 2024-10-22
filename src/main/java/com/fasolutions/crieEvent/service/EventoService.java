package com.fasolutions.crieEvent.service;

import com.fasolutions.crieEvent.domain.evento.Evento;
import com.fasolutions.crieEvent.domain.evento.exception.EventoNotFoundException;
import com.fasolutions.crieEvent.domain.participante.Participante;
import com.fasolutions.crieEvent.domain.evento.exception.VagasEsgotadasException;
import com.fasolutions.crieEvent.dto.request.EventoDTO.EventoIdDTO;
import com.fasolutions.crieEvent.dto.request.EventoDTO.EventoRequestDTO;
import com.fasolutions.crieEvent.dto.request.PariticpanteDTO.ParticipanteRequestDTO;
import com.fasolutions.crieEvent.dto.response.EventoDTO.EventoResponseDTO;
import com.fasolutions.crieEvent.dto.response.ParticipanteDTO.ParticipanteIdDTO;
import com.fasolutions.crieEvent.repositories.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {
    private final EventoRepository eventoRepository;
    private final ParticipanteService participanteService;

    /**Método para buscar participantes do evento pelo ID**/
    public EventoResponseDTO getEventoDetalhes(String idEvento){
        Evento evento = this.retornaEventoPorId(idEvento);
        List<Participante> listaParticipantes = this.participanteService.getAllParticipantesDoEvento(idEvento);

        return new EventoResponseDTO(evento, listaParticipantes.size());

    }

    /** Método que registra um novo participante **/
    public ParticipanteIdDTO registraParticipanteNoEvento(String eventoId, ParticipanteRequestDTO participanteRequestDTO){

        // chama o o verificador de participante
        Evento evento = this.retornaEventoPorId(eventoId);
        this.participanteService.verificaParticipante(participanteRequestDTO.email(), eventoId);
        List<Participante> listaParticipantes = this.participanteService.getAllParticipantesDoEvento(eventoId);

        // validação de limite de participantes.
        if (evento.getQtdMaxima() <= listaParticipantes.size()) throw new VagasEsgotadasException("Vagas esgotadas");

        //inica a construção do participante
        Participante novoParticipante = new Participante();
        novoParticipante.setNome(participanteRequestDTO.nome());
        novoParticipante.setEmail(participanteRequestDTO.email());
        novoParticipante.setEvento(evento);
        novoParticipante.setCriadoEm(LocalDateTime.now());

        Participante participanteCriado = this.participanteService.registraParticipante(novoParticipante);

        return new ParticipanteIdDTO(participanteCriado.getId());

    }

    /**Método para criar um novo evento**/
    public EventoIdDTO criarEvento(EventoRequestDTO eventoDTO){
        Evento novoEvento = new Evento();
        novoEvento.setNome(eventoDTO.nome());
        novoEvento.setDetalhes(eventoDTO.detalhes());
        novoEvento.setQtdMaxima(eventoDTO.qtdMaxima());
        novoEvento.setSlug(this.criaSlug(eventoDTO.nome()));

        //criando o retorno apenas do ID do evento.
        this.eventoRepository.save(novoEvento);

        return new EventoIdDTO(novoEvento.getId());
    }

    /** Método privado para criar um novo Slug automático**/
    private String criaSlug(String nome){
        String normalizar = Normalizer.normalize(nome, Normalizer.Form.NFD);
        return  normalizar.replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "")
                .toLowerCase();
    }

    /** Método privado para consultar o ID do evento, recem criado **/
    private Evento retornaEventoPorId(String eventoId){
        return this.eventoRepository.findById(eventoId).orElseThrow(() -> new EventoNotFoundException("Evento não encontrado"));
    }

}
