package com.Hotel_Buena_Vista.Service;

import com.Hotel_Buena_Vista.Repository.ServicioRepository;
import com.Hotel_Buena_Vista.domain.Servicio;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServicioService {
    private final ServicioRepository repository;
    public ServicioService(ServicioRepository repository){this.repository=repository;}
    public List<Servicio> filtrarServicios(String tipo, Boolean disponible){
        if(tipo!=null && !tipo.isBlank() && disponible!=null) return repository.findByTipoAndDisponible(tipo,disponible);
        if(tipo!=null && !tipo.isBlank()) return repository.findByTipo(tipo);
        if(disponible!=null) return repository.findByDisponible(disponible);
        return repository.findAll();
    }
    public List<Servicio> listar(){return repository.findAll();}
    public Servicio buscar(Long id){return repository.findById(id).orElse(null);}
    public Servicio guardar(Servicio s){
        if(s.getNombre()==null || s.getNombre().isBlank() || s.getPrecio()<0) throw new IllegalArgumentException("Datos incorrectos del servicio.");
        return repository.save(s);
    }
}
