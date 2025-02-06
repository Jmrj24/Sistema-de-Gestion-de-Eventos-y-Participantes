package com.example.sgep.service;

import com.example.sgep.dto.ParticipanteDTO;
import com.example.sgep.model.Participante;

import java.util.List;

public interface IParticipanteService {
    public void createParticipante(ParticipanteDTO participanteDto);

    public List<Participante> getAllParticipante();

    public String deleteParticipanteById(Long idP);

    public void editParticipante(Participante participante);

    public Participante findParticipanteById(Long idP);
}
