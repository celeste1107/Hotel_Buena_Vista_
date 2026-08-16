package com.Hotel_Buena_Vista.Contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    @GetMapping("/galeria")
    public String galeria() {
        return "galeria";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }
}
