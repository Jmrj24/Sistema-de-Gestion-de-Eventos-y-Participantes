package com.example.sgep.repository;

import com.example.sgep.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAsistenciaRepository extends JpaRepository<Asistencia, Long> {
    @Query("SELECT asis FROM Asistencia asis WHERE asis.evento.id=:idEvento AND asis.participante.id=:idParticipante")
    Asistencia findByEventoIdAndParticipanteId(@Param("idEvento") Long idEvento, @Param("idParticipante") Long idParticipante);

    @Query("SELECT asis FROM Asistencia asis WHERE asis.evento.id=:idEvento")
    List<Asistencia> asistenciaByEvento(@Param("idEvento") Long idEvento);

    @Query("SELECT asis FROM Asistencia asis WHERE asis.participante.id=:idParticipante")
    List<Asistencia> asistenciaByParticipante(@Param("idParticipante") Long idParticipante);
}
