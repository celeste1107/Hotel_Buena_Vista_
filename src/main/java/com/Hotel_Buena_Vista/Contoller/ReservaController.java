package com.Hotel_Buena_Vista.Contoller;

import com.Hotel_Buena_Vista.Repository.ReservarService;
import com.Hotel_Buena_Vista.Service.HuespedService;
import com.Hotel_Buena_Vista.domain.Reservas;
import com.Hotel_Buena_Vista.Service.ServicioService;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservarService reservarService;
    private final HuespedService huespedService;
    private final ServicioService servicioService;

    public ReservaController(ReservarService reservarService, HuespedService huespedService, ServicioService servicioService) {
        this.reservarService = reservarService;
        this.huespedService = huespedService;
        this.servicioService = servicioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("reservas", reservarService.listarReservas());
        return "reservas/listarReserva";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("huespedes", huespedService.listar());
        model.addAttribute("habitaciones", reservarService.habitacionesDisponibles(LocalDate.now(), LocalDate.now().plusDays(1)));
        model.addAttribute("reserva", new Reservas());
        model.addAttribute("servicios", servicioService.filtrarServicios(null, true));
        return "reservas/crearReserva";
    }

    @PostMapping("/crear")
    public String guardar(@RequestParam Long huespedId,
                          @RequestParam Long habitacionId,
                          @RequestParam LocalDate fechaEntrada,
                          @RequestParam LocalDate fechaSalida,
                          @RequestParam(required = false) java.util.List<Long> servicioIds,
                          Model model) {
        try {
            reservarService.crear(huespedId, habitacionId, fechaEntrada, fechaSalida, servicioIds);
            model.addAttribute("mensaje", "Reserva creada correctamente.");
            model.addAttribute("reservas", reservarService.listarReservas());
            return "reservas/listarReserva";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("huespedes", huespedService.listar());
            model.addAttribute("habitaciones", reservarService.habitacionesDisponibles(fechaEntrada, fechaSalida));
            model.addAttribute("servicios", servicioService.filtrarServicios(null, true));
            return "reservas/crearReserva";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Reservas reserva = reservarService.buscar(id);
        if (reserva == null) {
            model.addAttribute("error", "La reserva no existe.");
            model.addAttribute("reservas", reservarService.listarReservas());
            return "reservas/listarReserva";
        }
        model.addAttribute("reserva", reserva);
        model.addAttribute("huespedes", huespedService.listar());
        model.addAttribute("habitaciones", reservarService.habitacionesDisponibles(reserva.getFechaEntrada(), reserva.getFechaSalida()));
        model.addAttribute("servicios", servicioService.filtrarServicios(null, true));
        return "reservas/editarReserva";
    }

    @PostMapping("/editar")
    public String actualizar(@RequestParam Long id,
                             @RequestParam Long habitacionId,
                             @RequestParam LocalDate fechaEntrada,
                             @RequestParam LocalDate fechaSalida,
                             @RequestParam(required = false) java.util.List<Long> servicioIds,
                             Model model) {
        try {
            reservarService.actualizar(id, habitacionId, fechaEntrada, fechaSalida, servicioIds);
            model.addAttribute("mensaje", "Reserva actualizada correctamente.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("reservas", reservarService.listarReservas());
        return "reservas/listarReserva";
    }

    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, Model model) {
        try {
            reservarService.cancelarReserva(id);
            model.addAttribute("mensaje", "Reserva cancelada correctamente y habitación liberada.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("reservas", reservarService.listarReservas());
        return "reservas/listarReserva";
    }

    @PostMapping("/disponibles")
    public String disponibles(@RequestParam LocalDate fechaEntrada,
                              @RequestParam LocalDate fechaSalida,
                              Model model) {
        model.addAttribute("habitaciones", reservarService.habitacionesDisponibles(fechaEntrada, fechaSalida));
        model.addAttribute("huespedes", huespedService.listar());
        model.addAttribute("fechaEntrada", fechaEntrada);
        model.addAttribute("fechaSalida", fechaSalida);
        return "reservas/crearReserva";
    }
}
