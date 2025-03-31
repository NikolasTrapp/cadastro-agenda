package br.com.trapp.cadastroagendabackend.dao.impl;

import br.com.trapp.cadastroagendabackend.controller.payload.HospedePendenteDto;
import br.com.trapp.cadastroagendabackend.dao.HospedeDao;
import br.com.trapp.cadastroagendabackend.domain.QHospedeEntity;
import br.com.trapp.cadastroagendabackend.domain.QReservaEntity;
import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import br.com.trapp.cadastroagendabackend.dto.HospedeProjecao;
import br.com.trapp.cadastroagendabackend.util.FiltrosBusca;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.trapp.cadastroagendabackend.util.FiltroAplicador.aplicarFiltro;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@RequiredArgsConstructor
public class HospedeDaoImpl implements HospedeDao {

    private static final QHospedeEntity HOSPEDE = QHospedeEntity.hospedeEntity;
    private static final QReservaEntity RESERVA = QReservaEntity.reservaEntity;

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<HospedeProjecao> buscarPorNome(String nome) {
        var bean = Projections.bean(HospedeProjecao.class, HOSPEDE.id, HOSPEDE.nome);
        var query = new JPAQueryFactory(em);

        var where = new BooleanBuilder();

        if (isNotBlank(nome)) {
            where.and(HOSPEDE.nome.likeIgnoreCase("%"+nome+"%"));
        }

        return query.select(bean)
                .from(HOSPEDE)
                .where(where)
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HospedePendenteDto> buscarHospedesPendentesCheckIn(Pageable pageable) {
        var bean = hospedePendenteDtoProjection();
        var query = new JPAQueryFactory(em);

        var where = RESERVA.checkIn.isNull();

        var resultQuery = query.select(bean)
                .from(RESERVA)
                .innerJoin(HOSPEDE).on(RESERVA.hospede.eq(HOSPEDE))
                .where(where);

        var result = resultQuery.fetch();

        var countQuery = query.select(RESERVA.count())
                .from(RESERVA)
                .innerJoin(HOSPEDE).on(RESERVA.hospede.eq(HOSPEDE))
                .where(where);

        long total = countQuery.fetchOne();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HospedePendenteDto> buscarHospedesPendentesCheckOut(Pageable pageable) {
        var bean = hospedePendenteDtoProjection();
        var query = new JPAQueryFactory(em);

        var where = RESERVA.checkIn.isNotNull()
                .and(RESERVA.checkOut.isNull());

        var resultQuery = query.select(bean)
                .from(RESERVA)
                .innerJoin(HOSPEDE).on(RESERVA.hospede.eq(HOSPEDE))
                .where(where);

        var result = resultQuery.fetch();

        var countQuery = query.select(RESERVA.count())
                .from(RESERVA)
                .innerJoin(HOSPEDE).on(RESERVA.hospede.eq(HOSPEDE))
                .where(where);

        long total = countQuery.fetchOne();

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HospedeDto> list(Pageable pageable, FiltrosBusca filtrosBusca) {
        var bean = hospedeDtoProjection();
        var query = new JPAQueryFactory(em);
        var where = new BooleanBuilder();

        var filtros = aplicarFiltro(filtrosBusca.getFiltros(), HOSPEDE);
        where.and(filtros);

        var resultQuery = query.select(bean)
                .from(HOSPEDE)
                .where(where)
                .orderBy(HOSPEDE.id.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset());

        var result = resultQuery.fetch();

        var countQuery = query.select(HOSPEDE.count())
                .from(HOSPEDE)
                .where(where);

        long total = countQuery.fetchOne();

        return new PageImpl<>(result, pageable, total);
    }

    private QBean<HospedeDto> hospedeDtoProjection() {
        return Projections.bean(HospedeDto.class,
                HOSPEDE.id,
                HOSPEDE.nome,
                HOSPEDE.documento,
                HOSPEDE.telefone,
                HOSPEDE.dataNascimento
        );
    }

    private QBean<HospedePendenteDto> hospedePendenteDtoProjection() {
        return Projections.bean(HospedePendenteDto.class,
                HOSPEDE.id,
                HOSPEDE.nome,
                HOSPEDE.documento,
                HOSPEDE.telefone,
                HOSPEDE.dataNascimento,
                RESERVA.checkIn,
                RESERVA.checkOut
        );
    }

}
