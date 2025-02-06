package com.example.sgep.service;

import com.example.sgep.dto.EventoDTO;
import com.example.sgep.model.Evento;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEventoService {
    public void createEvento(EventoDTO eventoDto);

    public List<Evento> getAllEventos();

    public String deleteEventoById(Long idE);

    public void editEvento(Evento evento);

    public Evento findEventoById(Long idE);

    public String inscripcionEvento(Long idEvento, Long idParticipante);

    public String cancelarInscripEvento(Long idEvento, Long idParticipante);

    public ResponseEntity<byte[]> reporteParticipanteByIdEvento(Long idEvento);
}
