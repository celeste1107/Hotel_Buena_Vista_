package com.Hotel_Buena_Vista.Contoller;

import com.Hotel_Buena_Vista.Service.inicioSeccionService;
import com.Hotel_Buena_Vista.domain.InicioSeccion;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InicioSeccionController {

    private final inicioSeccionService inicioSeccionService;

    @Autowired
    public InicioSeccionController(inicioSeccionService inicioSeccionService){
        this.inicioSeccionService = inicioSeccionService;
    }

    // Mostrar login
    @GetMapping("/inicioSeccion")
    public String mostrarInicio() {
        return "inicioSeccion"; 
    }

    // Procesar login
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model){

        InicioSeccion usuario = inicioSeccionService.buscarPorEmail(email);

        if(usuario != null){
            if (BCrypt.checkpw(password, usuario.getPassword_hash())) { // ojo, tu getter se llama getPassword_hash()
                session.setAttribute("usuario", usuario);
                return "redirect:/principal";
            }
        }

        model.addAttribute("error", "Email o contraseña incorrectos");
        return "inicioSeccion";
    }

    // Mostrar página principal
    @GetMapping("/principal")
    public String principal(HttpSession session, Model model){
        InicioSeccion usuario = (InicioSeccion) session.getAttribute("usuario");
        if(usuario == null) return "redirect:/inicioSeccion";

        model.addAttribute("nombre", usuario.getNombre());
        return "principal"; // principal.html
    }
}
