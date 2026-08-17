package com.Hotel_Buena_Vista.Contoller;
import com.Hotel_Buena_Vista.Repository.EmpleadoRepository; import com.Hotel_Buena_Vista.domain.Empleado; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/empleados") public class EmpleadoController{
 private final EmpleadoRepository repo; public EmpleadoController(EmpleadoRepository repo){this.repo=repo;}
 @GetMapping public String listar(Model m){m.addAttribute("empleados",repo.findAll());return "empleados/listar";}
 @GetMapping("/nuevo") public String nuevo(Model m){m.addAttribute("empleado",new Empleado());return "empleados/formulario";}
 @PostMapping("/guardar") public String guardar(@ModelAttribute Empleado e){repo.save(e);return "redirect:/empleados";}
 @GetMapping("/editar/{id}") public String editar(@PathVariable Long id,Model m){m.addAttribute("empleado",repo.findById(id).orElseThrow());return "empleados/formulario";}
 @GetMapping("/eliminar/{id}") public String eliminar(@PathVariable Long id){repo.deleteById(id);return "redirect:/empleados";}
}
