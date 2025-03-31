package br.com.trapp.cadastroagendabackend.visitor.impl;

import br.com.trapp.cadastroagendabackend.domain.HospedeEntity;
import br.com.trapp.cadastroagendabackend.exception.HospedeException;
import br.com.trapp.cadastroagendabackend.visitor.HospedeVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HospedeVisitorImplTests {

    HospedeVisitor hospedeVisitor;

    @BeforeEach
    void setUp() {
        hospedeVisitor = new HospedeVisitorImpl();
    }

    @Test
    @DisplayName("Dada uma data de nascimento, quando for menor que 18 anos, deve gerar excecao.")
    void testaDataNascimentoInvalida() {
        var hospede = HospedeEntity.builder()
                .dataNascimento(LocalDate.now())
                .build();

        assertThatExceptionOfType(HospedeException.class)
                .isThrownBy(() -> hospedeVisitor.visitBeforeCreate(hospede))
                .withMessage("É necessário ter pelo menos 18 anos.");
    }

    @Test
    @DisplayName("Dada uma data de nascimento, quando for maior que 18 anos, deve permitir.")
    void testaDataNascimentoValida() {
        var hospede = HospedeEntity.builder()
                .dataNascimento(LocalDate.now().minusYears(19))
                .build();

        assertDoesNotThrow(() -> hospedeVisitor.visitBeforeCreate(hospede));
    }

    @Test
    @DisplayName("Dada uma data de nascimento, quando for nula, nao deve gerar excecao.")
    void testaDataNascimentoNula() {
        var hospede = HospedeEntity.builder()
                .dataNascimento(null)
                .build();

        assertDoesNotThrow(() -> hospedeVisitor.visitBeforeCreate(hospede));
    }
}
