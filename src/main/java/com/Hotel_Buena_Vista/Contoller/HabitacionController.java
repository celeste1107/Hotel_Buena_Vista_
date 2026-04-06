/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Hotel_Buena_Vista.Contoller;

import com.Hotel_Buena_Vista.domain.Habitacion;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author tetec
 */
public class HabitacionController {
    @GetMapping("/habitaciones")
public String verHabitaciones(Model model) {
    List<Habitacion> habitaciones = HabitacionRepository.findAll();
    model.addAttribute("habitaciones", habitaciones);
    return "habitaciones";
}
    
    
}
