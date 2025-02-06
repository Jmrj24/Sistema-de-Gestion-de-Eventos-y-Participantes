package com.example.sgep.controller;

import com.example.sgep.dto.EventoDTO;

import com.example.sgep.model.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.sgep.service.IEventoService;

import java.util.List;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    private IEventoService eventoServ;

    @PostMapping("/crear")
    public String createEvento(@RequestBody EventoDTO eventoDto) {
        eventoServ.createEvento(eventoDto);
        return "El evento ha sido creado con exito!";
    }

    @GetMapping("/traer")
    public List<Evento> getAllEventos() {
        return eventoServ.getAllEventos();
    }

    @DeleteMapping("/borrar/{idE}")
    public String deleteEvento(@PathVariable Long idE) {
        return eventoServ.deleteEventoById(idE);
    }

    @PutMapping("/editar")
    public Evento editEvento(@RequestBody Evento evento) {
        eventoServ.editEvento(evento);
        return eventoServ.findEventoById(evento.getId());
    }

    @PutMapping("/inscripcion/{idEvento}/{idParticipante}")
    public String inscripcionEvento(@PathVariable Long idEvento,
                                    @PathVariable Long idParticipante) {
        return eventoServ.inscripcionEvento(idEvento, idParticipante);
    }

    @PutMapping("/cancelar/{idEvento}/{idParticipante}")
    public String cancelarInscripEvento(@PathVariable Long idEvento,
                                        @PathVariable Long idParticipante) {
        return eventoServ.cancelarInscripEvento(idEvento, idParticipante);
    }

    @GetMapping("/reporte/{idEvento}")
    public ResponseEntity<byte[]> generarReporte(@PathVariable Long idEvento) {
        return eventoServ.reporteParticipanteByIdEvento(idEvento);
    }
}
