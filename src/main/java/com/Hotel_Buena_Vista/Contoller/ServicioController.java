package com.Hotel_Buena_Vista.Contoller;

import com.Hotel_Buena_Vista.Service.ServicioService;
import com.Hotel_Buena_Vista.domain.Servicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ServicioController {
    private final ServicioService service;
    public ServicioController(ServicioService service){this.service=service;}

    @GetMapping("/servicios")
    public String verServicios(@RequestParam(required=false) String tipo,
                               @RequestParam(required=false) Boolean disponible, Model model){
        model.addAttribute("servicios",service.filtrarServicios(tipo,disponible));
        return "servicios";
    }
    @GetMapping("/admin/servicios/nuevo")
    public String nuevo(Model model){model.addAttribute("servicio",new Servicio()); return "servicios/formulario";}
    @PostMapping("/admin/servicios/guardar")
    public String guardar(@ModelAttribute Servicio servicio, Model model){
        try { service.guardar(servicio); model.addAttribute("mensaje","Servicio guardado correctamente."); }
        catch(IllegalArgumentException e){model.addAttribute("error",e.getMessage()); return "servicios/formulario";}
        return "redirect:/servicios";
    }
    @GetMapping("/admin/servicios/editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        Servicio s=service.buscar(id); if(s==null){model.addAttribute("error","El servicio no existe."); return "servicios";}
        model.addAttribute("servicio",s); return "servicios/formulario";
    }
}
