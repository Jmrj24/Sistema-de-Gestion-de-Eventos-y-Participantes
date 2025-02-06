package com.example.sgep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AsistenciaByParticipanteDTO {
    private String nombreP;
    private String apellidoP;
    private String correo;
    private List<EventoDTO> listaEventos;
}
