package com.Hotel_Buena_Vista.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "habitaciones")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habitacion")
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String numero;

    @Column(nullable = false)
    private int piso;

    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(nullable = false)
    private int capacidad;

    @Column(name = "precio_noche", nullable = false)
    private double precio;

    @Column(nullable = false, length = 30)
    private String estado = "disponible";

    @OneToMany(mappedBy = "habitacion")
    private List<Reservas> reservas;

    public Habitacion() {}

    public Habitacion(Long id, String numero, String tipo, int capacidad, double precio, String estado) {
        this.id = id;
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.precio = precio;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public int getPiso() { return piso; }
    public void setPiso(int piso) { this.piso = piso; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public boolean isDisponible() {
        return "disponible".equalsIgnoreCase(estado);
    }

    public void setDisponible(boolean disponible) {
        this.estado = disponible ? "disponible" : "ocupada";
    }

    public List<Reservas> getReservas() { return reservas; }
    public void setReservas(List<Reservas> reservas) { this.reservas = reservas; }
}
