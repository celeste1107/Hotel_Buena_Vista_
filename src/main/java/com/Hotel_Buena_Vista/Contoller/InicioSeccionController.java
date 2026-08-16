package com.Hotel_Buena_Vista.Contoller;

import com.Hotel_Buena_Vista.Repository.InicioSeccionRepository;
import com.Hotel_Buena_Vista.Service.inicioSeccionService;
import com.Hotel_Buena_Vista.domain.InicioSeccion;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class InicioSeccionController {

    private final inicioSeccionService service;
    private final InicioSeccionRepository repository;

    @Autowired
    public InicioSeccionController(inicioSeccionService service, InicioSeccionRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/inicioSeccion")
    public String mostrarInicio() { return "inicioSeccion"; }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            model.addAttribute("error", "Datos incorrectos");
            return "inicioSeccion";
        }

        InicioSeccion usuario = service.buscarPorEmail(email.trim());
        if (usuario == null) {
            model.addAttribute("error", "Usuario no registrado");
            return "inicioSeccion";
        }

        if (!BCrypt.checkpw(password, usuario.getPassword_hash())) {
            model.addAttribute("error", "Datos incorrectos");
            return "inicioSeccion";
        }

        session.setAttribute("usuario", usuario);
        return "redirect:/principal";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam String nombre,
                            @RequestParam String apellido,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String password2,
                            Model model) {
        if (nombre == null || nombre.isBlank() || apellido == null || apellido.isBlank()
                || email == null || email.isBlank() || password == null || password.length() < 8
                || !password.equals(password2)) {
            model.addAttribute("error", "Datos incorrectos");
            return "registro";
        }

        if (repository.findByEmail(email.trim()) != null) {
            model.addAttribute("error", "El correo electrónico ya está registrado");
            return "registro";
        }

        InicioSeccion usuario = new InicioSeccion();
        usuario.setNombre(nombre.trim() + " " + apellido.trim());
        usuario.setEmail(email.trim());
        usuario.setPassword_hash(BCrypt.hashpw(password, BCrypt.gensalt()));
        usuario.setRol("usuario");
        repository.save(usuario);

        return "redirect:/inicioSeccion?registrado=true";
    }

    @GetMapping("/principal")
    public String principal(HttpSession session, Model model) {
        InicioSeccion usuario = (InicioSeccion) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/inicioSeccion";
        model.addAttribute("nombre", usuario.getNombre());
        return "principal";
    }
}
