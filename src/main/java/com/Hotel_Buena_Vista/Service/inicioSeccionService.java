package com.Hotel_Buena_Vista.Service;

import com.Hotel_Buena_Vista.Repository.InicioSeccionRepository;
import com.Hotel_Buena_Vista.domain.InicioSeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class inicioSeccionService {

    private final InicioSeccionRepository inicioSeccionRepository;

    @Autowired
    public inicioSeccionService(InicioSeccionRepository inicioSeccionRepository){
        this.inicioSeccionRepository = inicioSeccionRepository;
    }

    public InicioSeccion buscarPorEmail(String email){
        return inicioSeccionRepository.findByEmail(email);
    }
}
