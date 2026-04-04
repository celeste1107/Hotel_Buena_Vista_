package com.Hotel_Buena_Vista.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "habitacion")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private int capacidad;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false)
    private boolean disponible;

    // Relación con reservas
    @OneToMany(mappedBy = "habitacion")
    private List<Reservas> reservas;

    // Constructor vacío (IMPORTANTE para JPA)
    public Habitacion() {
    }

    // Constructor completo
    public Habitacion(Long id, String tipo, int capacidad, double precio, boolean disponible) {
        this.id = id;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.precio = precio;
        this.disponible = disponible;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<Reservas> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reservas> reservas) {
        this.reservas = reservas;
    }
}
