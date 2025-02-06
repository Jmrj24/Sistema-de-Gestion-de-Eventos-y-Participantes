package com.example.sgep.service;

import com.example.sgep.model.Notifications;
import com.example.sgep.model.Participante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService implements INotificationsService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendNotification(Notifications notifications, Participante participante) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hello@demomailtrap.com");
        // Aqui para que el correo se envia a cada usuario se debe setear en setTo "participante.getCorreo()"
        message.setTo("jeferson24j@gmail.com");
        message.setSubject(notifications.getSubject());
        message.setText("Hola " + participante.getNombreP() + " " + participante.getApellidoP() + notifications.getText());
        javaMailSender.send(message);
    }
}
