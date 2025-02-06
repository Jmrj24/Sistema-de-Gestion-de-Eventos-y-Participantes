package com.example.sgep.service;

import com.example.sgep.dto.AsistenciaByEventoDTO;
import com.example.sgep.dto.AsistenciaByParticipanteDTO;
import com.example.sgep.model.Asistencia;
import com.example.sgep.model.Evento;
import com.example.sgep.model.Participante;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAsistenciaService {
    public void createAsistencia(Evento evento, Participante participante);

    public List<Asistencia> getAllAsistencia();

    public AsistenciaByEventoDTO getAllAsistenciaByEvento(Long idEvento);

    public AsistenciaByParticipanteDTO getAllAsistenciaByParticipante(Long idParticipante);

    public String editAsistencia(Long idEvento, Long idParticipante, int estadoAsistencia);

    public boolean deleteAsistencia(Long idEvento, Long idParticipante);

    public ResponseEntity<byte[]> reporteAsistenciaByIdEvento(Long idEvento);
}
