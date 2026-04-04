package com.Hotel_Buena_Vista.Contoller;

import com.Hotel_Buena_Vista.Repository.ReservarService;
import com.Hotel_Buena_Vista.domain.Reservas;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservarService reservarService;

    @PostMapping("/asignar-habitacion")
    public Reservas asignarHabitacion(@RequestParam Long reservaId,
                                      @RequestParam Long habitacionId) {

        return reservarService.asignarHabitacion(reservaId, habitacionId);
    }
    @GetMapping("/listar")
    public List<Reservas> listarReservas() {
        return reservarService.listarReservas();
    }
}

