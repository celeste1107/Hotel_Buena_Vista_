/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Hotel_Buena_Vista.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table (name="reserva")
public class Reservas{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // id BIGINT AUTO_INCREMENT PRIMARY KEY

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;  // fecha_entrada DATE NOT NULL

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;   // fecha_salida DATE NOT NULL

    @ManyToOne
    @JoinColumn(name = "huesped_id", nullable = false)
    private Huesped huesped;  

    public Reservas(Long id, LocalDate fechaEntrada, LocalDate fechaSalida, Huesped huesped) {
        this.id = id;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.huesped = huesped;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    
}
