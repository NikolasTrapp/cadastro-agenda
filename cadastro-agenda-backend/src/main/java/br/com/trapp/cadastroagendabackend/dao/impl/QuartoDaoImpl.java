package br.com.trapp.cadastroagendabackend.dao.impl;

import br.com.trapp.cadastroagendabackend.dao.QuartoDao;
import br.com.trapp.cadastroagendabackend.domain.QQuartoEntity;
import br.com.trapp.cadastroagendabackend.dto.HospedeProjecao;
import br.com.trapp.cadastroagendabackend.dto.QuartoProjecao;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@RequiredArgsConstructor
public class QuartoDaoImpl implements QuartoDao {

    private static final QQuartoEntity QUARTO = QQuartoEntity.quartoEntity;

    @PersistenceContext
    private final EntityManager em;


    @Override
    @Transactional(readOnly = true)
    public List<QuartoProjecao> buscarQuartos(Long numeroQuarto) {
        var bean = Projections.bean(QuartoProjecao.class, QUARTO.id, QUARTO.numeroQuarto);
        var query = new JPAQueryFactory(em);

        var where = new BooleanBuilder();

        if (nonNull(numeroQuarto)) {
            where.and(QUARTO.numeroQuarto.like("%"+numeroQuarto+"%"));
        }

        return query.select(bean)
                .from(QUARTO)
                .where(where)
                .fetch();
    }
}
