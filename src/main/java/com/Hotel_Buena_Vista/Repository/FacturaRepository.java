package com.Hotel_Buena_Vista.Repository;
import com.Hotel_Buena_Vista.domain.Factura; import java.util.Optional; import org.springframework.data.jpa.repository.JpaRepository;
public interface FacturaRepository extends JpaRepository<Factura,Long>{Optional<Factura> findByReservaId(Long reservaId);}
