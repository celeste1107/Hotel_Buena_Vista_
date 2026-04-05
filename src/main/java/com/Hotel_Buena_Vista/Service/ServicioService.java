package com.Hotel_Buena_Vista.Service;

import com.Hotel_Buena_Vista.Repository.ServicioRepository;
import com.Hotel_Buena_Vista.domain.Servicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> filtrarServicios(String tipo, Boolean disponible) {

        if (tipo != null && !tipo.isEmpty() && disponible != null) {
            return servicioRepository.findByTipoAndDisponible(tipo, disponible);
        } else if (tipo != null && !tipo.isEmpty()) {
            return servicioRepository.findByTipo(tipo);
        } else if (disponible != null) {
            return servicioRepository.findByDisponible(disponible);
        } else {
            return servicioRepository.findAll();
        }
    }
}
