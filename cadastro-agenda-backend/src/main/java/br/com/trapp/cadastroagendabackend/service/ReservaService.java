package br.com.trapp.cadastroagendabackend.service;

import br.com.trapp.cadastroagendabackend.controller.payload.*;
import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;
import br.com.trapp.cadastroagendabackend.dto.ReservaDto;
import br.com.trapp.cadastroagendabackend.util.FiltrosBusca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReservaService {

    NovaReservaOutput novaReserva(NovaReservaInput input);
    EfetuarCheckoutOutput efetuarCheckin(EfetuarCheckinInput input);
    EfetuarCheckoutOutput efetuarCheckout(EfetuarCheckoutInput input);

    ReservaEntity getReservaById(UUID id);

    Page<ReservaDto> list(Pageable pageable, FiltrosBusca filtros);
}
