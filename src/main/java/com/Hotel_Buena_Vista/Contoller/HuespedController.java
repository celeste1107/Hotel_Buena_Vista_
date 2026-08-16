package com.Hotel_Buena_Vista.Contoller;

import com.Hotel_Buena_Vista.Service.HuespedService;
import com.Hotel_Buena_Vista.domain.Huesped;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/huespedes")
public class HuespedController {

    private final HuespedService service;

    public HuespedController(HuespedService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("huespedes", service.listar());
        return "huesped/listarHuespedes";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("huesped", new Huesped());
        return "huesped/agregarHuesped";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Huesped huesped, Model model) {
        try {
            service.guardar(huesped);
            model.addAttribute("mensaje", "Huésped registrado correctamente.");
            model.addAttribute("huespedes", service.listar());
            return "huesped/listarHuespedes";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "huesped/agregarHuesped";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Huesped huesped = service.buscar(id);
        if (huesped == null) {
            model.addAttribute("error", "El huésped no está registrado.");
            model.addAttribute("huespedes", service.listar());
            return "huesped/listarHuespedes";
        }
        model.addAttribute("huesped", huesped);
        return "huesped/editarHuespedes";
    }

    @PostMapping("/actualizar")
    public String actualizar(@ModelAttribute Huesped huesped, Model model) {
        if (huesped.getId() == null || !service.existe(huesped.getId())) {
            model.addAttribute("error", "El huésped no está registrado.");
            model.addAttribute("huespedes", service.listar());
            return "huesped/listarHuespedes";
        }
        try {
            service.guardar(huesped);
            model.addAttribute("mensaje", "Datos del huésped actualizados correctamente.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("huespedes", service.listar());
        return "huesped/listarHuespedes";
    }
}
