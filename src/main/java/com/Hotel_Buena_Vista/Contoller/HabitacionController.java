package com.Hotel_Buena_Vista.Contoller;

import com.Hotel_Buena_Vista.Repository.HabitacionRepository;
import com.Hotel_Buena_Vista.domain.Habitacion;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/habitaciones")
public class HabitacionController {

    private final HabitacionRepository repository;

    public HabitacionController(HabitacionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(@RequestParam(required=false) String tipo, @RequestParam(required=false) String numero, Model model) {
        var habitaciones = repository.findAll();
        if (tipo != null && !tipo.isBlank()) habitaciones = habitaciones.stream().filter(h -> tipo.equalsIgnoreCase(h.getTipo())).toList();
        if (numero != null && !numero.isBlank()) habitaciones = habitaciones.stream().filter(h -> numero.equalsIgnoreCase(h.getNumero())).toList();
        if (numero != null && !numero.isBlank() && habitaciones.isEmpty()) model.addAttribute("error", "La habitación no existe.");
        model.addAttribute("habitaciones", habitaciones);
        return "habitaciones/index";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, @RequestParam String estado, Model model) {
        Habitacion h = repository.findById(id).orElse(null);
        if (h == null) { model.addAttribute("error", "La habitación no existe."); return listar(null, null, model); }
        if (!java.util.List.of("disponible", "ocupada", "limpia", "mantenimiento", "reservada").contains(estado)) {
            model.addAttribute("error", "Estado no válido."); return listar(null, null, model);
        }
        h.setEstado(estado); repository.save(h);
        return "redirect:/habitaciones";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("habitacion", new Habitacion());
        return "habitaciones/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Habitacion habitacion, Model model) {
        if (habitacion.getNumero() == null || habitacion.getNumero().isBlank()
                || habitacion.getTipo() == null || habitacion.getTipo().isBlank()
                || habitacion.getCapacidad() <= 0 || habitacion.getPrecio() <= 0) {
            model.addAttribute("error", "Datos incorrectos o incompletos.");
            return "habitaciones/formulario";
        }
        if (habitacion.getEstado() == null || habitacion.getEstado().isBlank()) {
            habitacion.setEstado("disponible");
        }
        repository.save(habitacion);
        return "redirect:/habitaciones";
    }
}
