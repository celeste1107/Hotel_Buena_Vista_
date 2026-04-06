package com.Hotel_Buena_Vista.Repository;

import com.Hotel_Buena_Vista.domain.InicioSeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InicioSeccionRepository extends JpaRepository<InicioSeccion, Long> {
    InicioSeccion findByEmail(String email);
}