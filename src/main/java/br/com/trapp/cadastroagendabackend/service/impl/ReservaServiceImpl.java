package br.com.trapp.cadastroagendabackend.service.impl;

import br.com.trapp.cadastroagendabackend.component.AbrirTransacao;
import br.com.trapp.cadastroagendabackend.controller.payload.*;
import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;
import br.com.trapp.cadastroagendabackend.exception.ReservaException;
import br.com.trapp.cadastroagendabackend.mapper.ReservaMapper;
import br.com.trapp.cadastroagendabackend.repository.ReservaRepository;
import br.com.trapp.cadastroagendabackend.service.ReservaService;
import br.com.trapp.cadastroagendabackend.visitor.ReservaVisitor;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.trapp.cadastroagendabackend.util.Utils.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaMapper reservaMapper;
    private final ReservaVisitor reservaVisitor;
    private final AbrirTransacao abrirTransacao;
    private final ReservaRepository reservaRepository;

    @Override
    @Transactional
    public NovaReservaOutput novaReserva(NovaReservaInput input) {
        var reserva = input.getReserva();
        var reservaEntity = reservaMapper.toEntity(reserva);
        reservaVisitor.visitBeforeCreate(reservaEntity);

        reservaEntity = reservaRepository.save(reservaEntity);
        reservaVisitor.visitAfterCreate(reservaEntity);

        return NovaReservaOutput.builder()
                .reserva(reservaMapper.toDto(reservaEntity))
                .build();
    }

    @Override
    @Transactional
    public EfetuarCheckoutOutput efetuarCheckin(EfetuarCheckinInput input) {
        var reservaEntity = abrirTransacao.callInReadOnlyTransaction(() -> getReservaById(input.getReservaId()));

        if (nonNull(reservaEntity.getCheckIn())) {
            throw new ReservaException("Ja foi realizado o check-in da reserva.");
        }

        reservaEntity.setCheckIn(LocalDateTime.now());
        reservaVisitor.visitBeforeUpdate(reservaEntity);

        reservaEntity = reservaRepository.save(reservaEntity);
        reservaVisitor.visitAfterUpdate(reservaEntity);

        return EfetuarCheckoutOutput.builder().build();
    }

    @Override
    @Transactional
    public EfetuarCheckoutOutput efetuarCheckout(EfetuarCheckoutInput input) {
        var reservaEntity = abrirTransacao.callInReadOnlyTransaction(() -> getReservaById(input.getReservaId()));

        if (isNull(reservaEntity.getCheckIn())) {
            throw new ReservaException("Nao foi realizado o check-in da reserva.");
        }

        if (nonNull(reservaEntity.getCheckOut())) {
            throw new ReservaException("Ja foi realizado o check-out da reserva.");
        }

        reservaEntity.setCheckOut(LocalDateTime.now());
        reservaVisitor.visitBeforeUpdate(reservaEntity);

        reservaEntity = reservaRepository.save(reservaEntity);
        reservaVisitor.visitAfterUpdate(reservaEntity);

        return EfetuarCheckoutOutput.builder()
                .reserva(reservaMapper.toDto(reservaEntity))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaEntity getReservaById(UUID id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Reserva com ID: '{}' nao encontrada.", id)));
    }
}
