package br.com.trapp.cadastroagendabackend.service.impl;

import br.com.trapp.cadastroagendabackend.controller.payload.*;
import br.com.trapp.cadastroagendabackend.repository.ReservaRepository;
import br.com.trapp.cadastroagendabackend.service.ReservaService;
import br.com.trapp.cadastroagendabackend.visitor.ReservaVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaVisitor reservaVisitor;
    private final ReservaRepository reservaRepository;


    @Override
    public NovaReservaOutput novaReserva(NovaReservaInput input) {
        return null;
    }

    @Override
    public EfetuarCheckoutOutput efetuarCheckin(EfetuarCheckinInput input) {
        return null;
    }

    @Override
    public EfetuarCheckoutOutput efetuarCheckout(EfetuarCheckoutInput input) {
        return null;
    }
}
