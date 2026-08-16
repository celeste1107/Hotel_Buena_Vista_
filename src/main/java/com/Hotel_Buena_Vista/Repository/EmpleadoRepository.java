package com.Hotel_Buena_Vista.Repository;
import com.Hotel_Buena_Vista.domain.Empleado; import org.springframework.data.jpa.repository.JpaRepository;
public interface EmpleadoRepository extends JpaRepository<Empleado,Long>{}