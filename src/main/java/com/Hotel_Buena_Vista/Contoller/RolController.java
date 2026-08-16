package com.Hotel_Buena_Vista.Contoller;
import com.Hotel_Buena_Vista.Repository.EmpleadoRepository; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/roles") public class RolController{
 private final EmpleadoRepository repo; public RolController(EmpleadoRepository repo){this.repo=repo;}
 @GetMapping public String form(Model m){m.addAttribute("empleados",repo.findAll());return "empleados/roles";}
 @PostMapping("/asignar") public String asignar(@RequestParam Long empleadoId,@RequestParam String rol,Model m){var e=repo.findById(empleadoId).orElse(null); if(e==null){m.addAttribute("error","El empleado no existe.");m.addAttribute("empleados",repo.findAll());return "empleados/roles";} e.setRol(rol);repo.save(e);m.addAttribute("mensaje","Rol asignado correctamente.");m.addAttribute("empleados",repo.findAll());return "empleados/roles";}
}
