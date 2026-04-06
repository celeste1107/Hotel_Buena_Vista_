/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Hotel_Buena_Vista.domain;

/**
 *
 * @author ismal
 */
package com.hotel.Hotel_Buena_Vista.entidades;

import jakarta.persistence.*;

import jakarta.persistence.Entity;

import jakarta.persistence.Table;

import jakarta.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;

import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String metodo;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reservas reserva;

    // GETTERS Y SETTERS
    public Long getId() { return id; }

    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Reservas getReserva() { return reserva; }
    public void setReserva(Reservas reserva) { this.reserva = reserva; }
}
