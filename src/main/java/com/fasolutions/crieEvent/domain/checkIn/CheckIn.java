package com.fasolutions.crieEvent.domain.checkIn;

import com.fasolutions.crieEvent.domain.participante.Participante;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "check_ins")
@Getter
@Setter
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data_checkin")
    private LocalDateTime dataCheckin;

    @OneToOne
    @JoinColumn(name = "id_participante", nullable = false)
    private Participante participante;


}
