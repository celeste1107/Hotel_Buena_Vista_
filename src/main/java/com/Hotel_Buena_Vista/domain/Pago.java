package com.Hotel_Buena_Vista.domain;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="pagos") public class Pago{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @OneToOne(optional=false) @JoinColumn(name="id_reserva",unique=true) private Reservas reserva;
 @Column(nullable=false) private String metodo; @Column(nullable=false) private double monto; private String estado; private LocalDateTime fecha=LocalDateTime.now();
 public Pago(){} public Long getId(){return id;} public void setId(Long v){id=v;} public Reservas getReserva(){return reserva;} public void setReserva(Reservas v){reserva=v;} public String getMetodo(){return metodo;} public void setMetodo(String v){metodo=v;} public double getMonto(){return monto;} public void setMonto(double v){monto=v;} public String getEstado(){return estado;} public void setEstado(String v){estado=v;} public LocalDateTime getFecha(){return fecha;} public void setFecha(LocalDateTime v){fecha=v;}
}
