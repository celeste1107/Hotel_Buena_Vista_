package com.Hotel_Buena_Vista.Contoller;
import com.Hotel_Buena_Vista.Repository.*; import com.Hotel_Buena_Vista.domain.Factura; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/facturas") public class FacturaController{
 private final ReservaRepository reservas; private final FacturaRepository facturas; public FacturaController(ReservaRepository r,FacturaRepository f){reservas=r;facturas=f;}
 @GetMapping("/{reservaId}") public String generar(@PathVariable Long reservaId,Model m){var r=reservas.findById(reservaId).orElse(null); if(r==null){m.addAttribute("error","La reserva no existe. No se genera factura.");return "factura";} var f=facturas.findByReservaId(reservaId).orElseGet(Factura::new); f.setReserva(r); f.setTotal(r.getPrecioTotal()); facturas.save(f); m.addAttribute("factura",f); return "factura";}
}
