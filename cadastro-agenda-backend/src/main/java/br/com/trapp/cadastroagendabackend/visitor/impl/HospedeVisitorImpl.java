package br.com.trapp.cadastroagendabackend.visitor.impl;

import br.com.trapp.cadastroagendabackend.domain.HospedeEntity;
import br.com.trapp.cadastroagendabackend.exception.HospedeException;
import br.com.trapp.cadastroagendabackend.visitor.HospedeVisitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospedeVisitorImpl implements HospedeVisitor {
    private static final int IDADE_MINIMA = 18;

    @Override
    public void visitBeforeCreate(HospedeEntity entity) {
        validarIdadeMinima(entity.getDataNascimento());
    }

    public static void validarIdadeMinima(LocalDate dataNascimento) {
        if (isNull(dataNascimento)) {
            return;
        }

        var hoje = LocalDate.now();
        var idade = Period.between(dataNascimento, hoje).getYears();

        if (idade < IDADE_MINIMA) {
            throw new HospedeException("É necessário ter pelo menos 18 anos.");
        }
    }
}
