package com.example.sgep.controller;

import com.example.sgep.dto.AsistenciaByEventoDTO;
import com.example.sgep.dto.AsistenciaByParticipanteDTO;
import com.example.sgep.service.IAsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asistencia")
public class AsistenciaController {
    @Autowired
    private IAsistenciaService asistenciaServ;

    @PutMapping("/registrar/{idEvento}/{idParticipante}/{estadoAsistencia}")
    public String createAsistencia(@PathVariable Long idEvento,
                                   @PathVariable Long idParticipante,
                                   @PathVariable int estadoAsistencia) {
        return asistenciaServ.editAsistencia(idEvento, idParticipante, estadoAsistencia);
    }

    @GetMapping("/evento/{idEvento}")
    public AsistenciaByEventoDTO getAsistenciaByEvento(@PathVariable Long idEvento) {
        return asistenciaServ.getAllAsistenciaByEvento(idEvento);
    }

    @GetMapping("/participante/{idParticipante}")
    public AsistenciaByParticipanteDTO getAsistenciaByParticipante(@PathVariable Long idParticipante) {
        return asistenciaServ.getAllAsistenciaByParticipante(idParticipante);
    }

    @GetMapping("/reporte/{idEvento}")
    public ResponseEntity<byte[]> reporteAsistenciaByIdEvento(@PathVariable Long idEvento) {
        return asistenciaServ.reporteAsistenciaByIdEvento(idEvento);
    }
}
