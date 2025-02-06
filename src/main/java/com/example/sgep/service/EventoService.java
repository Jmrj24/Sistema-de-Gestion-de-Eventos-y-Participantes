package com.example.sgep.service;

import com.example.sgep.dto.EventoDTO;
import com.example.sgep.model.Evento;
import com.example.sgep.model.Notifications;
import com.example.sgep.model.Participante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.sgep.repository.IEventoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventoService implements IEventoService {
    @Autowired
    private IEventoRepository eventoRepo;

    @Autowired
    private IParticipanteService participanteServ;

    @Autowired
    private IAsistenciaService asistenciaServ;

    @Autowired
    private INotificationsService notificationsServ;

    @Autowired
    private IReporteService reporteServ;

    @Override
    public void createEvento(EventoDTO eventoDto) {
        Evento evento = new Evento();

        evento.setNombreE(eventoDto.getNombreE());
        evento.setDescripcion(eventoDto.getDescripcion());
        evento.setFechaHoraE(eventoDto.getFechaHoraE());
        evento.setCupoMax(eventoDto.getCupoMax());
        evento.setCupoDispo(eventoDto.getCupoMax());

        eventoRepo.save(evento);
    }

    @Override
    public List<Evento> getAllEventos() {
        return eventoRepo.findAll();
    }

    @Override
    public String deleteEventoById(Long idE) {
        String msj = "Evento no encontrado. Vuelve a intentarlo con un evento valido!";
        if(this.findEventoById(idE)!=null) {
            eventoRepo.deleteById(idE);
            msj = "Evento eliminado de forma exitosa!";
        }
        return msj;
    }

    @Override
    public void editEvento(Evento evento) {
        eventoRepo.save(evento);
    }

    @Override
    public Evento findEventoById(Long idE) {
        return eventoRepo.findById(idE).orElse(null);
    }

    @Override
    public String inscripcionEvento(Long idEvento, Long idParticipante) {
        Evento eventoById = this.findEventoById(idEvento);
        Participante participanteById = participanteServ.findParticipanteById(idParticipante);
        String mensaje = "";

        //Maneja que el evento y el participante a inscribir existan
        if((eventoById!=null)&&(participanteById!=null)) {
            //Maneja que aun quede cupo antes de inscribir un participante
            if(eventoById.getCupoDispo()>0) {
                //Maneja que la inscripcion se lleve a cabo solo antes del inicio del evento
                if(LocalDateTime.now().isBefore(eventoById.getFechaHoraE())) {
                    List<Participante> participantes = eventoById.getParticipantes();
                    //Maneja que un participante no se pueda inscribir varias veces en un mismo evento
                    if(participantes.contains(participanteById)) {
                        mensaje = "El participante que intentas inscribir ya se encuentra inscrito en el evento!";
                    } else {
                        int cupoDisp = eventoById.getCupoDispo();
                        participantes.add(participanteById);
                        cupoDisp--;

                        eventoById.setParticipantes(participantes);
                        eventoById.setCupoDispo(cupoDisp);

                        this.editEvento(eventoById);
                        asistenciaServ.createAsistencia(eventoById, participanteById);
                        mensaje = "La inscripcion del participante " + participanteById.getNombreP() +" "+ participanteById.getApellidoP() +
                                " en el evento " + eventoById.getNombreE() + " se realizó con exito!";
                        notificationsServ.sendNotification(new Notifications ("Inscripción Realizada",
                                " has sido inscrito de manera correcta en el evento " + eventoById.getNombreE() +
                                        " que se celebrará el dia " + eventoById.getFechaHoraE().toLocalDate() +
                                        " recuerda confirmar tu asistencia, te esperamos!"), participanteById);
                    }
                } else {
                    mensaje = "La inscripción no se realizó debido a que caducó la fecha de inscripción para este evento.";
                }
            } else {
                mensaje = "No es posible realizar la inscripcion ya que el evento no tiene cupos disponibles";
            }
        } else {
            mensaje = "El evento y/o el participante que intentas inscribir no existe!";
        }
        return mensaje;
    }

    @Override
    public String cancelarInscripEvento(Long idEvento, Long idParticipante) {
        Evento eventoById = this.findEventoById(idEvento);
        Participante participanteById = participanteServ.findParticipanteById(idParticipante);
        String mensaje;

        if(eventoById!=null) {
            if(participanteById!=null) {
                if(asistenciaServ.deleteAsistencia(eventoById.getId(), participanteById.getId())) {
                    List<Participante> participantes = eventoById.getParticipantes();
                    int cupoDisp = eventoById.getCupoDispo();

                    participantes.remove(participanteById);
                    cupoDisp++;
                    eventoById.setParticipantes(participantes);
                    eventoById.setCupoDispo(cupoDisp);
                    this.editEvento(eventoById);

                    mensaje = "La inscripcion del participante " + participanteById.getNombreP() + " al evento " +
                            eventoById.getNombreE() + " ha sido cancelada";
                    notificationsServ.sendNotification(new Notifications ("Inscripción Cancelada",
                            " tu inscripción al evento " + eventoById.getNombreE() +
                                    " ha sido cancelada."), participanteById);
                } else {
                    mensaje = "El participante " + participanteById.getNombreP() + " no tiene registro de inscripcion al evento "+
                    eventoById.getNombreE();
                }
            } else {
                mensaje = "No existe un participante asociado a el dato que ingresaste";
            }
        } else {
            mensaje = "No existe un evento asociado a el dato que ingresaste";
        }
        return mensaje;
    }

    @Override
    public ResponseEntity<byte[]> reporteParticipanteByIdEvento(Long idEvento) {
        return reporteServ.reporteParticipanteByIdEvento(this.findEventoById(idEvento));
    }
}
