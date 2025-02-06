package com.example.sgep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipanteAsistenciaDTO {
    private String nombreP;
    private String apellidoP;
    private String estadoAsistencia;
}
