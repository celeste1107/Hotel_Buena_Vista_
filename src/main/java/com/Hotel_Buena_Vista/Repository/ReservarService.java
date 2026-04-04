package com.Hotel_Buena_Vista.Repository;

import com.Hotel_Buena_Vista.Repository.HabitacionRepository;
import com.Hotel_Buena_Vista.Repository.ReservaRepository;
import com.Hotel_Buena_Vista.domain.Habitacion;
import com.Hotel_Buena_Vista.domain.Reservas;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservarService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    public Reservas asignarHabitacion(Long reservaId, Long habitacionId) {

        Reservas reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Habitacion habitacion = habitacionRepository.findById(habitacionId)
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        if (!habitacion.isDisponible()) {
            throw new RuntimeException("Habitación no disponible");
        }

        reserva.setHabitacion(habitacion);
        habitacion.setDisponible(false);

        habitacionRepository.save(habitacion);

        return reservaRepository.save(reserva);
    }

    public List<Reservas> listarReservas() {
        return reservaRepository.findAll();
    }
    public void cancelarReserva(Long id) {
    Reservas reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

  
    Habitacion habitacion = reserva.getHabitacion();
    if (habitacion != null) {
        habitacion.setDisponible(true);
        habitacionRepository.save(habitacion);
    }

    reservaRepository.deleteById(id);
}
}