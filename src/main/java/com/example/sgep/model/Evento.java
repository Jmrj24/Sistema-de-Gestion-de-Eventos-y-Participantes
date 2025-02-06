package com.example.sgep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombreE;
    private String descripcion;
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime fechaHoraE;
    private int cupoMax;
    private int cupoDispo;
    @ManyToMany
    private List<Participante> participantes;
}
