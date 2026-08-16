package com.Hotel_Buena_Vista.domain;
import jakarta.persistence.*;
@Entity @Table(name="empleados")
public class Empleado {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String nombre;
 @Column(nullable=false,unique=true) private String email;
 private String telefono;
 @Column(nullable=false) private String rol="recepcionista";
 public Empleado(){} public Long getId(){return id;} public void setId(Long v){id=v;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getTelefono(){return telefono;} public void setTelefono(String v){telefono=v;} public String getRol(){return rol;} public void setRol(String v){rol=v;}
}
