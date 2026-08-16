package com.Hotel_Buena_Vista.Repository;

import com.Hotel_Buena_Vista.domain.Habitacion;
import com.Hotel_Buena_Vista.domain.Huesped;
import com.Hotel_Buena_Vista.domain.Reservas;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservarService {

    private final ReservaRepository reservaRepository;
    private final HabitacionRepository habitacionRepository;
    private final HuespedRepository huespedRepository;
    private final ServicioRepository servicioRepository;

    public ReservarService(ReservaRepository reservaRepository,
                           HabitacionRepository habitacionRepository,
                           HuespedRepository huespedRepository,
                           ServicioRepository servicioRepository) {
        this.reservaRepository = reservaRepository;
        this.habitacionRepository = habitacionRepository;
        this.huespedRepository = huespedRepository;
        this.servicioRepository = servicioRepository;
    }

    public List<Reservas> listarReservas() { return reservaRepository.findAll(); }

    public Reservas buscar(Long id) { return reservaRepository.findById(id).orElse(null); }

    public List<Habitacion> habitacionesDisponibles(LocalDate entrada, LocalDate salida) {
        if (entrada == null || salida == null || !salida.isAfter(entrada)) return List.of();
        return habitacionRepository.findAll().stream()
                .filter(Habitacion::isDisponible)
                .filter(h -> reservaRepository.buscarSolapadas(h.getId(), entrada, salida).isEmpty())
                .toList();
    }

    @Transactional
    public Reservas crear(Long huespedId, Long habitacionId, LocalDate entrada, LocalDate salida, List<Long> servicioIds) {
        if (entrada == null || salida == null || !salida.isAfter(entrada))
            throw new IllegalArgumentException("Las fechas son incorrectas.");

        Huesped huesped = huespedRepository.findById(huespedId)
                .orElseThrow(() -> new IllegalArgumentException("El huésped no está registrado."));
        Habitacion habitacion = habitacionRepository.findById(habitacionId)
                .orElseThrow(() -> new IllegalArgumentException("La habitación no existe."));

        if (!reservaRepository.buscarSolapadas(habitacionId, entrada, salida).isEmpty())
            throw new IllegalArgumentException("No hay disponibilidad para esas fechas.");

        long noches = ChronoUnit.DAYS.between(entrada, salida);
        Reservas reserva = new Reservas();
        reserva.setHuesped(huesped);
        reserva.setHabitacion(habitacion);
        reserva.setFechaEntrada(entrada);
        reserva.setFechaSalida(salida);
        List<com.Hotel_Buena_Vista.domain.Servicio> servicios = servicioIds == null ? List.of() : servicioRepository.findAllById(servicioIds);
        double totalServicios = servicios.stream().mapToDouble(com.Hotel_Buena_Vista.domain.Servicio::getPrecio).sum();
        reserva.setServicios(servicios);
        reserva.setPrecioTotal(noches * habitacion.getPrecio() + totalServicios);
        reserva.setEstado("confirmada");

        habitacion.setEstado("reservada");
        habitacionRepository.save(habitacion);
        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reservas actualizar(Long id, Long habitacionId, LocalDate entrada, LocalDate salida, List<Long> servicioIds) {
        Reservas reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La reserva no existe."));
        if (entrada == null || salida == null || !salida.isAfter(entrada))
            throw new IllegalArgumentException("Las fechas son incorrectas.");

        if (!reservaRepository.buscarSolapadas(habitacionId, entrada, salida).stream()
                .filter(r -> !r.getId().equals(id)).toList().isEmpty())
            throw new IllegalArgumentException("No hay disponibilidad para esas fechas.");

        Habitacion nueva = habitacionRepository.findById(habitacionId)
                .orElseThrow(() -> new IllegalArgumentException("La habitación no existe."));
        Habitacion anterior = reserva.getHabitacion();
        if (anterior != null && !anterior.getId().equals(nueva.getId())) {
            anterior.setEstado("disponible");
            habitacionRepository.save(anterior);
        }

        long noches = ChronoUnit.DAYS.between(entrada, salida);
        reserva.setHabitacion(nueva);
        reserva.setFechaEntrada(entrada);
        reserva.setFechaSalida(salida);
        List<com.Hotel_Buena_Vista.domain.Servicio> servicios = servicioIds == null ? List.of() : servicioRepository.findAllById(servicioIds);
        double totalServicios = servicios.stream().mapToDouble(com.Hotel_Buena_Vista.domain.Servicio::getPrecio).sum();
        reserva.setServicios(servicios);
        reserva.setPrecioTotal(noches * nueva.getPrecio() + totalServicios);
        nueva.setEstado("reservada");
        habitacionRepository.save(nueva);
        return reservaRepository.save(reserva);
    }

    @Transactional
    public void cancelarReserva(Long id) {
        Reservas reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La reserva no existe."));
        Habitacion habitacion = reserva.getHabitacion();
        if (habitacion != null) {
            habitacion.setEstado("disponible");
            habitacionRepository.save(habitacion);
        }
        reserva.setEstado("cancelada");
        reservaRepository.save(reserva);
    }
}
