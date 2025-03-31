package br.com.trapp.cadastroagendabackend.service.impl;

import br.com.trapp.cadastroagendabackend.component.AbrirTransacao;
import br.com.trapp.cadastroagendabackend.controller.payload.*;
import br.com.trapp.cadastroagendabackend.dao.ReservaDao;
import br.com.trapp.cadastroagendabackend.domain.HospedeEntity;
import br.com.trapp.cadastroagendabackend.domain.QuartoEntity;
import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;
import br.com.trapp.cadastroagendabackend.dto.ReservaDto;
import br.com.trapp.cadastroagendabackend.exception.ReservaException;
import br.com.trapp.cadastroagendabackend.mapper.ReservaMapper;
import br.com.trapp.cadastroagendabackend.repository.ReservaRepository;
import br.com.trapp.cadastroagendabackend.service.ReservaService;
import br.com.trapp.cadastroagendabackend.util.FiltrosBusca;
import br.com.trapp.cadastroagendabackend.visitor.ReservaVisitor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.trapp.cadastroagendabackend.util.Utils.format;
import static java.util.Objects.*;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    @PersistenceContext
    private final EntityManager em;
    private final ReservaMapper reservaMapper;
    private final ReservaVisitor reservaVisitor;
    private final AbrirTransacao abrirTransacao;
    private final ReservaRepository reservaRepository;
    private final ReservaDao reservaDao;

    @Override
    @Transactional
    public NovaReservaOutput novaReserva(NovaReservaInput input) {
        var reserva = input.getReserva();
        var reservaEntity = reservaMapper.toEntity(reserva);

        //Como o front envia uma entidade resumida, aqui o hospede e o quarto estao sem a maioria dos campos e detached da sessao, entao
        //quando o hibernate fizer o merge para o contexto vai dar erro. Para resolver isso vamos forcar elas no contexto
        var hospedeEntity = em.find(HospedeEntity.class, reservaEntity.getHospede().getId());
        reservaEntity.setHospede(requireNonNull(hospedeEntity, format("Hospede com ID '{}' nao encontrado", reservaEntity.getHospede().getId())));
        var quartoEntity = em.find(QuartoEntity.class, reservaEntity.getQuarto().getId());
        reservaEntity.setQuarto(requireNonNull(quartoEntity, format("Quarto com ID '{}' nao encontrado", reservaEntity.getQuarto().getId())));

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

    @Override
    @Transactional(readOnly = true)
    public Page<ReservaDto> list(Pageable pageable, FiltrosBusca filtros) {
        return reservaDao.list(pageable, filtros);
    }
}
