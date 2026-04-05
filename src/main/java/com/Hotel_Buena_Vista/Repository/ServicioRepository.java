package com.Hotel_Buena_Vista.Repository;

import com.Hotel_Buena_Vista.domain.Servicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByDisponible(boolean disponible);

    List<Servicio> findByTipo(String tipo);

    List<Servicio> findByTipoAndDisponible(String tipo, boolean disponible);
}
