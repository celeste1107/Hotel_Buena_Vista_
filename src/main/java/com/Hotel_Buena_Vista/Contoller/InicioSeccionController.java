/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Hotel_Buena_Vista.Contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class InicioSeccionController {

    @GetMapping("/inicioSeccion")
    public String mostrarInicio() {
        return "inicioSeccion"; // Spring busca inicioSeccion.html en templates
    }
}
