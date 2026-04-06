/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Hotel_Buena_Vista.Contoller;

/**
 *
 * @author ismal
 */
@Controller
public class PagoController {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    // MOSTRAR PAGO
    @GetMapping("/pago/{id}")
    public String mostrarPago(@PathVariable Long id, Model model) {

        Reservas reserva = reservaRepository.findById(id).orElse(null);

        Pago pago = new Pago();
        pago.setReserva(reserva);

        model.addAttribute("pago", pago);

        return "pago";
    }

    // PROCESAR PAGO
    @PostMapping("/pago")
    public String procesarPago(@ModelAttribute Pago pago, Model model) {

       
        if (pago.getMetodo() == null || pago.getMetodo().isEmpty()) {

            pago.setEstado("ERROR");

            model.addAttribute("error", "Método de pago inválido o no seleccionado");
            return "pago";
        }

  
        pago.setEstado("COMPLETADO");

        pagoRepository.save(pago);

        model.addAttribute("mensaje", "Pago realizado correctamente");

        return "confirmacionPago";
    }
}