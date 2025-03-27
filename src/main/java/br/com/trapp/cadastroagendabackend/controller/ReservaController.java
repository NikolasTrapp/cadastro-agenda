package br.com.trapp.cadastroagendabackend.controller;

import br.com.trapp.cadastroagendabackend.controller.payload.*;
import br.com.trapp.cadastroagendabackend.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reserva")
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping(name = "/nova-reserva")
    public ResponseEntity<NovaReservaOutput> novaReserva(@Valid @RequestBody NovaReservaInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.novaReserva(input));
    }

    @PostMapping(name = "/efetuar-checkin")
    public ResponseEntity<EfetuarCheckoutOutput> efetuarCheckin(@Valid @RequestBody EfetuarCheckinInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.efetuarCheckin(input));
    }

    @PostMapping(name = "/efetuar-checkout")
    public ResponseEntity<EfetuarCheckoutOutput> efetuarCheckout(@Valid @RequestBody EfetuarCheckoutInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.efetuarCheckout(input));
    }

}
