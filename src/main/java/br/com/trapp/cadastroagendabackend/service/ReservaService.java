package br.com.trapp.cadastroagendabackend.service;

import br.com.trapp.cadastroagendabackend.controller.payload.*;

public interface ReservaService {

    NovaReservaOutput novaReserva(NovaReservaInput input);
    EfetuarCheckoutOutput efetuarCheckin(EfetuarCheckinInput input);
    EfetuarCheckoutOutput efetuarCheckout(EfetuarCheckoutInput input);
}
