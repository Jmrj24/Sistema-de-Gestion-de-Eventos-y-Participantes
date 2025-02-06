package com.example.sgep.repository;

import com.example.sgep.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IParticipanteRepository extends JpaRepository<Participante, Long> {
}
