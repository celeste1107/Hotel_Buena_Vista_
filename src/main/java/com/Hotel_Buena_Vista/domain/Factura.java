package com.Hotel_Buena_Vista.domain;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="facturas") public class Factura{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @OneToOne(optional=false) @JoinColumn(name="id_reserva",unique=true) private Reservas reserva;
 @Column(nullable=false) private double total; private LocalDateTime fecha=LocalDateTime.now();
 public Factura(){} public Long getId(){return id;} public void setId(Long v){id=v;} public Reservas getReserva(){return reserva;} public void setReserva(Reservas v){reserva=v;} public double getTotal(){return total;} public void setTotal(double v){total=v;} public LocalDateTime getFecha(){return fecha;} public void setFecha(LocalDateTime v){fecha=v;}
}


