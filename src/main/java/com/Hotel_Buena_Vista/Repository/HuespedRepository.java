package com.Hotel_Buena_Vista.Repository;

import com.Hotel_Buena_Vista.domain.Huesped;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HuespedRepository extends JpaRepository<Huesped, Long> {
    Optional<Huesped> findByEmail(String email);
    Optional<Huesped> findByDocumentoIdentidad(String documentoIdentidad);
}
