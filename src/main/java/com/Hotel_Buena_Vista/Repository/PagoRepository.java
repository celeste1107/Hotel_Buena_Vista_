package com.Hotel_Buena_Vista.Repository;
import com.Hotel_Buena_Vista.domain.Pago; import java.util.Optional; import org.springframework.data.jpa.repository.JpaRepository;
public interface PagoRepository extends JpaRepository<Pago,Long>{Optional<Pago> findByReservaId(Long reservaId);}
