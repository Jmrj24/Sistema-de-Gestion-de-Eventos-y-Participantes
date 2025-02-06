package com.example.sgep.service;

import com.example.sgep.dto.ParticipanteDTO;
import com.example.sgep.model.Participante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sgep.repository.IParticipanteRepository;

import java.util.List;

@Service
public class ParticipanteService implements IParticipanteService {
    @Autowired
    private IParticipanteRepository participanteRepo;

    @Override
    public void createParticipante(ParticipanteDTO participanteDto) {
        Participante participante = new Participante();

        participante.setNombreP(participanteDto.getNombreP());
        participante.setApellidoP(participanteDto.getApellidoP());
        participante.setCorreo(participanteDto.getCorreo());
        participanteRepo.save(participante);
    }

    @Override
    public List<Participante> getAllParticipante() {
        return participanteRepo.findAll();
    }

    @Override
    public String deleteParticipanteById(Long idP) {
        String msj = "Participante no encontrado. Vuelve a intentarlo con un participante valido!";
        if(this.findParticipanteById(idP)!=null) {
            participanteRepo.deleteById(idP);
            msj = "Participante eliminado de forma exitosa!";
        }
        return msj;
    }

    @Override
    public void editParticipante(Participante participante) {
        participanteRepo.save(participante);
    }

    @Override
    public Participante findParticipanteById(Long idP) {
        return participanteRepo.findById(idP).orElse(null);
    }
}
