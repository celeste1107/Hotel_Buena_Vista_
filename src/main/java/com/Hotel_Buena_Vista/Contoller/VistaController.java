package com.Hotel_Buena_Vista.Contoller;

import com.Hotel_Buena_Vista.Repository.ReservarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VistaController {

    @Autowired
    private ReservarService reservarService;

    // Mostrar todas las reservas
    @GetMapping("/ver-reservas")
    public String verReservas(Model model) {
        model.addAttribute("reservas", reservarService.listarReservas());
        return "reservas";
    }

    // Cancelar reserva
    @GetMapping("/reservas/cancelar/{id}")
    public String cancelarReserva(@PathVariable Long id) {
        reservarService.cancelarReserva(id);
        return "redirect:/ver-reservas";
    }
}