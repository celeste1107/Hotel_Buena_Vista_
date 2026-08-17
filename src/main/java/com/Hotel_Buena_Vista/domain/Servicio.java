package com.Hotel_Buena_Vista.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "servicios")
public class Servicio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible = true;
    private String tipo;

    @ManyToMany(mappedBy = "servicios")
    private List<Reservas> reservas;

    public Servicio() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
    public String getDescripcion(){return descripcion;} public void setDescripcion(String v){descripcion=v;}
    public double getPrecio(){return precio;} public void setPrecio(double v){precio=v;}
    public boolean isDisponible(){return disponible;} public void setDisponible(boolean v){disponible=v;}
    public String getTipo(){return tipo;} public void setTipo(String v){tipo=v;}
    public List<Reservas> getReservas(){return reservas;} public void setReservas(List<Reservas> v){reservas=v;}
}
