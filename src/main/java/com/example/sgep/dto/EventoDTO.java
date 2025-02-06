package com.example.sgep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO {
    private String nombreE;
    private String descripcion;
    private LocalDateTime fechaHoraE;
    private int cupoMax;
    private String estadoAsistenciaParticipante;
}
