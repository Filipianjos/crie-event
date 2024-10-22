package com.fasolutions.crieEvent.service;

import com.fasolutions.crieEvent.domain.checkIn.CheckIn;
import com.fasolutions.crieEvent.domain.participante.Participante;
import com.fasolutions.crieEvent.domain.participante.exception.ParticipanteJaResgistradoException;
import com.fasolutions.crieEvent.domain.participante.exception.ParticipanteNotFoundException;
import com.fasolutions.crieEvent.dto.request.PariticpanteDTO.ParticipanteDetalhes;
import com.fasolutions.crieEvent.dto.request.PariticpanteDTO.ParticipanteCrachaRequestDTO;
import com.fasolutions.crieEvent.dto.response.ParticipanteDTO.ParticipanteCrachaResponseDTO;
import com.fasolutions.crieEvent.dto.response.ParticipanteDTO.ParticipanteListaResponseDTO;
import com.fasolutions.crieEvent.repositories.ParticipanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipanteService {
    private final ParticipanteRepository participanteRepository;
    private final CheckInService checkInService;

    /**Método para buscar listar os participantes de um evento**/
    public List<Participante> getAllParticipantesDoEvento(String idEvento){
        return this.participanteRepository.findByEvento_id(idEvento);
    }

    public ParticipanteListaResponseDTO getEventosParticipante(String idEvento){
        List<Participante> listaParticipante = this.getAllParticipantesDoEvento(idEvento);

        List<ParticipanteDetalhes> participanteDetalhes = listaParticipante
                .stream()
                .map(participante -> {
                    Optional<CheckIn> checkIn = this.checkInService.getCheckIn(participante.getId());
                    LocalDateTime checkedInEm = checkIn.<LocalDateTime>map(CheckIn::getDataCheckin).orElse(null);
                    return new ParticipanteDetalhes(participante.getId(), participante.getNome(), participante.getEmail(), participante.getCriadoEm(), checkedInEm);
        }).toList();

        return new ParticipanteListaResponseDTO(participanteDetalhes);
    }

    /** Registro de um novo participante**/
    public Participante registraParticipante(Participante novoParticipante){
        this.participanteRepository.save(novoParticipante);
        return  novoParticipante;
    }

    /** Método para verificar se já existe um participante registrado no evento **/
    public void verificaParticipante(String email, String eventoId){
      Optional<Participante> estaRegistrado = this.participanteRepository.findByEvento_idAndEmail(eventoId, email);

        if (estaRegistrado.isPresent()) throw new ParticipanteJaResgistradoException("Participante já registrado");
    }

    /** Método de visualização do crachá **/
    public ParticipanteCrachaResponseDTO getParticipanteCracha(String participanteId, UriComponentsBuilder uriComponentsBuilder){
        Participante participante = this.getParticipante(participanteId);

        //criação do URI para inserir no atributo do crachaDTO
        var uri = uriComponentsBuilder.path("/participante/{participanteId}/check-in").buildAndExpand(participanteId).toUri().toString();

        //criando o objeto crachá do participante
        ParticipanteCrachaRequestDTO crachaDTO = new ParticipanteCrachaRequestDTO(
                participante.getNome(),
                participante.getEmail(),
                uri,
                participante.getEvento().getId()
        );

        return new ParticipanteCrachaResponseDTO(crachaDTO);
    }

    public void checkInParticipante(String participanteId){
        Participante participante = this.getParticipante(participanteId);
        this.checkInService.checkInParticipante(participante);

    }

    /** Método para localizar o participante pelo ID**/
    private Participante getParticipante(String participanteId){
        return this.participanteRepository.findById(participanteId).orElseThrow(() -> new ParticipanteNotFoundException("Participante não localizado" + participanteId));
    }
}

