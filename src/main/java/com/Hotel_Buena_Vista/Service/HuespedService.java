package com.Hotel_Buena_Vista.Service;

import com.Hotel_Buena_Vista.Repository.HuespedRepository;
import com.Hotel_Buena_Vista.domain.Huesped;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HuespedService {

    private final HuespedRepository repository;

    public HuespedService(HuespedRepository repository) {
        this.repository = repository;
    }

    public List<Huesped> listar() { return repository.findAll(); }

    public Huesped buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Huesped guardar(Huesped huesped) {
        if (huesped.getNombre() == null || huesped.getNombre().isBlank()
                || huesped.getApellido() == null || huesped.getApellido().isBlank()
                || huesped.getDocumentoIdentidad() == null || huesped.getDocumentoIdentidad().isBlank()) {
            throw new IllegalArgumentException("Datos incorrectos");
        }
        repository.findByDocumentoIdentidad(huesped.getDocumentoIdentidad())
                .filter(existente -> huesped.getId() == null || !existente.getId().equals(huesped.getId()))
                .ifPresent(existente -> { throw new IllegalArgumentException("El huésped ya está registrado"); });
        return repository.save(huesped);
    }

    public boolean existe(Long id) { return repository.existsById(id); }
}
