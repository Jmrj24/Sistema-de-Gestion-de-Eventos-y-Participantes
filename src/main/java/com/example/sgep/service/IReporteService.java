package com.example.sgep.service;

import com.example.sgep.dto.AsistenciaByEventoDTO;
import com.example.sgep.model.Evento;
import org.springframework.http.ResponseEntity;

public interface IReporteService {
    public ResponseEntity<byte[]> reporteParticipanteByIdEvento(Evento evento);

    public ResponseEntity<byte[]> reporteAsistenciaByIdEvento(AsistenciaByEventoDTO asistenciaByEvento);
}
