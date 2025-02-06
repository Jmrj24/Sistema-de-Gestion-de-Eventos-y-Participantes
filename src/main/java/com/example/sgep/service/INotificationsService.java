package com.example.sgep.service;

import com.example.sgep.model.Notifications;
import com.example.sgep.model.Participante;

public interface INotificationsService {
    public void sendNotification(Notifications notifications, Participante participante);
}
