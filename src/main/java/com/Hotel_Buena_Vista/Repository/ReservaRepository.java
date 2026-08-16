package com.Hotel_Buena_Vista.Repository;

import com.Hotel_Buena_Vista.domain.Reservas;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservaRepository extends JpaRepository<Reservas, Long> {

    @Query("select r from Reservas r where r.habitacion.id = :habitacionId "
         + "and r.estado <> 'cancelada' and r.fechaEntrada < :salida and r.fechaSalida > :entrada")
    List<Reservas> buscarSolapadas(@Param("habitacionId") Long habitacionId,
                                   @Param("entrada") LocalDate entrada,
                                   @Param("salida") LocalDate salida);
}

