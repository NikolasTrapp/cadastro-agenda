package br.com.trapp.cadastroagendabackend.service;

import br.com.trapp.cadastroagendabackend.controller.payload.*;
import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;

import java.util.UUID;

public interface ReservaService {

    NovaReservaOutput novaReserva(NovaReservaInput input);
    EfetuarCheckoutOutput efetuarCheckin(EfetuarCheckinInput input);
    EfetuarCheckoutOutput efetuarCheckout(EfetuarCheckoutInput input);

    ReservaEntity getReservaById(UUID id);
}
