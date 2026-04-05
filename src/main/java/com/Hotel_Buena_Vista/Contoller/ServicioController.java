package com.Hotel_Buena_Vista.Contoller;

import com.Hotel_Buena_Vista.Service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping("/servicios")
    public String verServicios(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Boolean disponible,
            Model model) {

        model.addAttribute("servicios",
                servicioService.filtrarServicios(tipo, disponible));

        return "servicios";
    }
}