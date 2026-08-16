package com.Hotel_Buena_Vista.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "huespedes")
public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_huesped")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Column(name = "documento_identidad", nullable = false, unique = true, length = 50)
    private String documentoIdentidad;

    @Column(name = "tipo_documento", length = 30)
    private String tipoDocumento = "cedula";

    @Column(length = 80)
    private String nacionalidad;

    @OneToMany(mappedBy = "huesped")
    private List<Reservas> reservas;

    public Huesped() {}

    public Huesped(Long id, String nombre, String apellido, String email, String telefono,
                   String documentoIdentidad, String tipoDocumento, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.documentoIdentidad = documentoIdentidad;
        this.tipoDocumento = tipoDocumento;
        this.nacionalidad = nacionalidad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDocumentoIdentidad() { return documentoIdentidad; }
    public void setDocumentoIdentidad(String documentoIdentidad) { this.documentoIdentidad = documentoIdentidad; }
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public List<Reservas> getReservas() { return reservas; }
    public void setReservas(List<Reservas> reservas) { this.reservas = reservas; }
}
