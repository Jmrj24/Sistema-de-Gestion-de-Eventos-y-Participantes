package com.example.sgep.controller;

import com.example.sgep.dto.ParticipanteDTO;
import com.example.sgep.model.Participante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.sgep.service.IParticipanteService;

import java.util.List;

@RestController
@RequestMapping("/participante")
public class ParticipanteController {
    @Autowired
    private IParticipanteService participanteServ;

    @PostMapping("/crear")
    public String createParticipante(@RequestBody ParticipanteDTO participanteDto) {
        participanteServ.createParticipante(participanteDto);
        return "Participante creado con exito!";
    }

    @GetMapping("/traer")
    public List<Participante> getAllParticipante() {
        return participanteServ.getAllParticipante();
    }

    @DeleteMapping("/borrar/{idP}")
    public String deleteParticipanteById(@PathVariable Long idP) {
        return participanteServ.deleteParticipanteById(idP);
    }

    @PutMapping("/editar")
    public Participante editParticipante(@RequestBody Participante participante) {
        participanteServ.editParticipante(participante);
        return participanteServ.findParticipanteById(participante.getId());
    }

}
