package com.Hotel_Buena_Vista.Contoller;
import com.Hotel_Buena_Vista.Repository.*; import com.Hotel_Buena_Vista.domain.*; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/pagos") public class PagoController{
 private final PagoRepository pagos; private final ReservaRepository reservas; public PagoController(PagoRepository p,ReservaRepository r){pagos=p;reservas=r;}
 @GetMapping("/{id}") public String form(@PathVariable Long id,Model m){var r=reservas.findById(id).orElse(null); if(r==null){m.addAttribute("error","La reserva no existe.");return "pago";} m.addAttribute("reserva",r);return "pago";}
 @PostMapping public String pagar(@RequestParam Long reservaId,@RequestParam String metodo,Model m){var r=reservas.findById(reservaId).orElse(null); if(r==null){m.addAttribute("error","La reserva no existe.");return "pago";} if(!java.util.List.of("sinpe","tarjeta","efectivo","transferencia").contains(metodo)){m.addAttribute("error","Método de pago inválido o no disponible.");m.addAttribute("reserva",r);return "pago";} Pago p=new Pago();p.setReserva(r);p.setMetodo(metodo);p.setMonto(r.getPrecioTotal());p.setEstado("aprobado");pagos.save(p);m.addAttribute("mensaje","Pago procesado correctamente.");m.addAttribute("pago",p);return "pago";}
}
