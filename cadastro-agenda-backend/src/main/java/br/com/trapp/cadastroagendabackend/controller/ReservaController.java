package br.com.trapp.cadastroagendabackend.controller;

import br.com.trapp.cadastroagendabackend.controller.payload.*;
import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import br.com.trapp.cadastroagendabackend.dto.ReservaDto;
import br.com.trapp.cadastroagendabackend.service.ReservaService;
import br.com.trapp.cadastroagendabackend.util.FiltrosBusca;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PostMapping("/nova-reserva")
    public ResponseEntity<NovaReservaOutput> novaReserva(@Valid @RequestBody NovaReservaInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.novaReserva(input));
    }

    @PostMapping("/efetuar-checkin")
    public ResponseEntity<EfetuarCheckoutOutput> efetuarCheckin(@Valid @RequestBody EfetuarCheckinInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.efetuarCheckin(input));
    }

    @PostMapping("/efetuar-checkout")
    public ResponseEntity<EfetuarCheckoutOutput> efetuarCheckout(@Valid @RequestBody EfetuarCheckoutInput input) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.efetuarCheckout(input));
    }

    @PostMapping("/listar")
    public ResponseEntity<Page<ReservaDto>> buscarPaginado(Pageable pageable, @RequestBody FiltrosBusca filtros) {
        return ResponseEntity.status(HttpStatus.OK).body(reservaService.list(pageable, filtros));
    }

}
