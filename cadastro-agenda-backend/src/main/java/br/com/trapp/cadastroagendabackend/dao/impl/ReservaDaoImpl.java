package br.com.trapp.cadastroagendabackend.dao.impl;

import br.com.trapp.cadastroagendabackend.dao.ReservaDao;
import br.com.trapp.cadastroagendabackend.domain.QReservaEntity;
import br.com.trapp.cadastroagendabackend.dto.*;
import br.com.trapp.cadastroagendabackend.util.FiltrosBusca;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static br.com.trapp.cadastroagendabackend.util.FiltroAplicador.aplicarFiltro;

@Component
@RequiredArgsConstructor
public class ReservaDaoImpl implements ReservaDao {

    private static final QReservaEntity RESERVA = QReservaEntity.reservaEntity;

    @PersistenceContext
    private final EntityManager em;


    @Override
    @Transactional(readOnly = true)
    public Page<ReservaDto> list(Pageable pageable, FiltrosBusca filtrosBusca) {
        var bean = getReservaDtoBean();
        var query = new JPAQueryFactory(em);
        var where = new BooleanBuilder();

        var filtros = aplicarFiltro(filtrosBusca.getFiltros(), RESERVA);
        where.and(filtros);

        var resultQuery = query.select(bean)
                .from(RESERVA)
                .where(where)
                .orderBy(RESERVA.id.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        var result = resultQuery.fetch();

        var countQuery = query.select(RESERVA.count())
                .from(RESERVA)
                .where(where);

        long total = countQuery.fetchOne();

        return new PageImpl<>(result, pageable, total);
    }

    private QBean<ReservaDto> getReservaDtoBean() {
        var hospedeBean = Projections.bean(HospedeDto.class,
                RESERVA.hospede.id,
                RESERVA.hospede.nome
        );
        var quartoBean = Projections.bean(QuartoDto.class,
                RESERVA.quarto.id,
                RESERVA.quarto.numeroQuarto
        );
        return Projections.bean(
                ReservaDto.class,
                RESERVA.id,
                RESERVA.dataEntrada,
                RESERVA.dataSaida,
                RESERVA.checkIn,
                RESERVA.checkOut,
                RESERVA.numeroPessoas,
                RESERVA.necessitaEstacionamento,
                RESERVA.valor,
                RESERVA.valorMulta,
                hospedeBean.as("hospede"),
                quartoBean.as("quarto")
        );
    }
}
