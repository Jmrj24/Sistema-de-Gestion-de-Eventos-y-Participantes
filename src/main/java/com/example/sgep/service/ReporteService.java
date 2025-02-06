package com.example.sgep.service;

import com.example.sgep.dto.AsistenciaByEventoDTO;
import com.example.sgep.dto.ParticipanteAsistenciaDTO;
import com.example.sgep.model.Evento;
import com.example.sgep.model.Participante;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

@Service
public class ReporteService implements IReporteService {

    @Override
    public ResponseEntity<byte[]> reporteParticipanteByIdEvento(Evento evento) {
        List listaParticipantes = new List(List.UNORDERED);
        for(Participante parti: evento.getParticipantes()) {
            listaParticipantes.add(new ListItem(parti.getNombreP() + " " + parti.getApellidoP() + " Correo: " + parti.getCorreo()));
        }
        return crearPdf("Reporte de Participantes inscritos en Evento", evento.getNombreE(), evento.getDescripcion(),
                        evento.getFechaHoraE(), evento.getCupoMax(), listaParticipantes);
    }

    @Override
    public ResponseEntity<byte[]> reporteAsistenciaByIdEvento(AsistenciaByEventoDTO asistenciaByEvento) {
        List listaParticipantes = new List(List.UNORDERED);
        for(ParticipanteAsistenciaDTO parti:asistenciaByEvento.getListaParticipantes()) {
            listaParticipantes.add(new ListItem(parti.getNombreP() + " " + parti.getApellidoP() + " Estado de asistencia: " + parti.getEstadoAsistencia()));
        }
        return crearPdf("Reporte de Asistencia en Evento", asistenciaByEvento.getNombreE(),
                asistenciaByEvento.getDescripcion(), asistenciaByEvento.getFechaHoraE(), asistenciaByEvento.getCupoMax(), listaParticipantes);
    }

    public ResponseEntity<byte[]> crearPdf(String tituloPdf, String nombreE, String descripcion, LocalDateTime fechaE, int cupoMax, List listaParticipantes) {
        try {
            // Crear documento PDF
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Agregar contenido al PDF
            Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph titulo = new Paragraph(tituloPdf , fontNegrita);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("El Evento: " + nombreE + " Sobre: " + descripcion));
            document.add(new Paragraph("Celebrado: " + fechaE.toLocalDate()));
            document.add(new Paragraph("El cúal tiene un cupo máximo de " + cupoMax + " participantes"));
            document.add(new Paragraph("Tiene inscritos " + listaParticipantes.size() + " participantes"));
            document.add(new Paragraph("A continuación tienes el listado de los participantes: "));
            document.add(listaParticipantes);
            document.close();

            // Convertir el PDF en un arreglo de bytes
            byte[] contenidoPDF = outputStream.toByteArray();

            // Configurar respuesta HTTP con el PDF adjunto
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "reporte.pdf");

            return new ResponseEntity<>(contenidoPDF, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
