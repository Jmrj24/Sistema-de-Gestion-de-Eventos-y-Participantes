package com.example.sgep.service;

import com.example.sgep.dto.*;
import com.example.sgep.model.Asistencia;
import com.example.sgep.model.Evento;
import com.example.sgep.model.Notifications;
import com.example.sgep.model.Participante;
import com.example.sgep.repository.IAsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AsistenciaService implements IAsistenciaService {
    @Autowired
    private IAsistenciaRepository asistenciaRepo;

    @Autowired
    private INotificationsService notificationsServ;

    @Autowired
    private IReporteService reporteServ;

    @Override
    public void createAsistencia(Evento evento, Participante participante) {
        Asistencia asistencia = new Asistencia();
        asistencia.setEvento(evento);
        asistencia.setParticipante(participante);
        asistencia.setEstadoAsistencia("Sin Confirmar");
        asistenciaRepo.save(asistencia);
    }

    @Override
    public List<Asistencia> getAllAsistencia() {
        return asistenciaRepo.findAll();
    }

    @Override
    public AsistenciaByEventoDTO getAllAsistenciaByEvento(Long idEvento) {
        List<Asistencia> listaAsistencia = asistenciaRepo.asistenciaByEvento(idEvento);

        if(!listaAsistencia.isEmpty()) {
            Evento eventoById = listaAsistencia.get(0).getEvento();
            List<ParticipanteAsistenciaDTO> listaParticipantes = new ArrayList<>();
            ParticipanteAsistenciaDTO participante;
            AsistenciaByEventoDTO asistenciaByEvento = new AsistenciaByEventoDTO();
            for(Asistencia a:listaAsistencia) {
                participante = new ParticipanteAsistenciaDTO();
                participante.setNombreP(a.getParticipante().getNombreP());
                participante.setApellidoP(a.getParticipante().getApellidoP());
                participante.setEstadoAsistencia(a.getEstadoAsistencia());
                listaParticipantes.add(participante);
            }
            asistenciaByEvento.setNombreE(eventoById.getNombreE());
            asistenciaByEvento.setDescripcion(eventoById.getDescripcion());
            asistenciaByEvento.setCupoMax(eventoById.getCupoMax());
            asistenciaByEvento.setFechaHoraE(eventoById.getFechaHoraE());
            asistenciaByEvento.setListaParticipantes(listaParticipantes);
            return asistenciaByEvento;
        } else {
            return null;
        }
    }

    @Override
    public AsistenciaByParticipanteDTO getAllAsistenciaByParticipante(Long idParticipante) {
        List<Asistencia> listaAsistencia = asistenciaRepo.asistenciaByParticipante(idParticipante);

        if(!listaAsistencia.isEmpty()) {
            Participante participante = listaAsistencia.get(0).getParticipante();
            List<EventoDTO> listaEventos = new ArrayList<>();
            EventoDTO evento;
            AsistenciaByParticipanteDTO asistenciaByParticipante = new AsistenciaByParticipanteDTO();

            for(Asistencia a:listaAsistencia) {
                evento = new EventoDTO();
                evento.setNombreE(a.getEvento().getNombreE());
                evento.setDescripcion(a.getEvento().getDescripcion());
                evento.setFechaHoraE(a.getEvento().getFechaHoraE());
                evento.setCupoMax(a.getEvento().getCupoMax());
                evento.setEstadoAsistenciaParticipante(a.getEstadoAsistencia());
                listaEventos.add(evento);
            }
            asistenciaByParticipante.setNombreP(participante.getNombreP());
            asistenciaByParticipante.setApellidoP(participante.getApellidoP());
            asistenciaByParticipante.setCorreo(participante.getCorreo());
            asistenciaByParticipante.setListaEventos(listaEventos);
            return asistenciaByParticipante;
        } else {
            return null;
        }
    }

    @Override
    public String editAsistencia(Long idEvento, Long idParticipante, int estadoAsistencia) {
        String mensaje = "No fue posible registrar la asistencia, ya que no existe un participante asociado al evento ingresado." +
                " Verifica los datos ingresados";

        if(estadoAsistencia>1||estadoAsistencia<0) {
            mensaje = "Debes seleccionar seleccionar una opcion valida para registrar el estado de la asistencia.";
        } else {
            Asistencia asistencia = asistenciaRepo.findByEventoIdAndParticipanteId(idEvento, idParticipante);

            if(asistencia!=null) {
                // Valida que el registro de asistencia solo pueda realizarse el dia del evento
                LocalDate diaEvento = asistencia.getEvento().getFechaHoraE().toLocalDate();
                if(diaEvento.isEqual(LocalDate.now())) {
                    String textNotifications = "";
                    if(estadoAsistencia==0) {
                        asistencia.setEstadoAsistencia("Ausente");
                        textNotifications = " hemos registrado como ausente tu asistencia al evento " + asistencia.getEvento().getNombreE() +
                                ", lamentamos que no puedas asistir.";
                    } else {
                        asistencia.setEstadoAsistencia("Confirmada");
                        textNotifications = " hemos confirmado tu asistencia al evento " + asistencia.getEvento().getNombreE() +
                                ", esperamos que disfrutes mucho!";
                    }
                    asistenciaRepo.save(asistencia);
                    mensaje = "Asistencia Registrada con exito!";
                    notificationsServ.sendNotification(new Notifications("Asistencia Registrada", textNotifications), asistencia.getParticipante());
                } else {
                    mensaje = "La asistencia solo la puedes registrar el dia del evento.";
                }
            }
        }
        return mensaje;
    }

    @Override
    public boolean deleteAsistencia(Long idEvento, Long idParticipante) {
        boolean band = false;
        Asistencia asis = asistenciaRepo.findByEventoIdAndParticipanteId(idEvento, idParticipante);
        if(asis!=null) {
            asistenciaRepo.deleteById(asis.getId());
            band = true;
        }
        return band;
    }

    @Override
    public ResponseEntity<byte[]> reporteAsistenciaByIdEvento(Long idEvento) {
        return reporteServ.reporteAsistenciaByIdEvento(this.getAllAsistenciaByEvento(idEvento));
    }
}
