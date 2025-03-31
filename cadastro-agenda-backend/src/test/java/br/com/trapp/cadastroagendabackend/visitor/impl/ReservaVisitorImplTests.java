package br.com.trapp.cadastroagendabackend.visitor.impl;

import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;
import br.com.trapp.cadastroagendabackend.exception.ReservaException;
import br.com.trapp.cadastroagendabackend.visitor.ReservaVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static br.com.trapp.cadastroagendabackend.visitor.impl.ReservaVisitorImpl.*;
import static org.assertj.core.api.Assertions.*;

class ReservaVisitorImplTests {

    private ReservaVisitor reservaVisitor;

    @BeforeEach
    void setUp() {
        reservaVisitor = new ReservaVisitorImpl();
    }

    @Test
    @DisplayName("Dado uma reserva válida, quando calcular o valor, então deve somar corretamente os valores das diárias e taxa estacionamento")
    void dadoReservaValida_quandoCalcularValor_entaoDeveSomarCorretamente() {
        var reserva = ReservaEntity.builder()
                .dataEntrada(LocalDate.of(2025, 3, 3))
                .dataSaida(LocalDate.of(2025, 3, 9))
                .necessitaEstacionamento(true)
                .numeroPessoas((short) 1)
                .build();

        reservaVisitor.visitBeforeCreate(reserva);

        var diasUteis = BigDecimal.valueOf(5);
        var finaisDeSemana = BigDecimal.valueOf(2);
        var valorExpected = VALOR_DIARIA_DIAS_UTEIS.multiply(diasUteis)
                .add(VALOR_DIARIA_FINAIS_SEMANA.multiply(finaisDeSemana))
                .add(VALOR_TAXA_ESTACIONAMENTO_DIAS_UTEIS.multiply(diasUteis))
                .add(VALOR_TAXA_ESTACIONAMENTO_FINAIS_SEMANA.multiply(finaisDeSemana));
        assertThat(reserva.getValor()).isEqualTo(valorExpected);
    }

    @Test
    @DisplayName("Dado uma reserva válida, quando calcular o valor, então deve somar corretamente os valores das diárias sem taxa estacionamento")
    void dadoReservaValida_quandoCalcularValor_entaoDeveSomarCorretamenteSemTaxa() {
        var reserva = ReservaEntity.builder()
                .dataEntrada(LocalDate.of(2025, 3, 3))
                .dataSaida(LocalDate.of(2025, 3, 9))
                .necessitaEstacionamento(false)
                .numeroPessoas((short) 1)
                .build();

        reservaVisitor.visitBeforeCreate(reserva);

        var diasUteis = BigDecimal.valueOf(5);
        var finaisDeSemana = BigDecimal.valueOf(2);
        var valorExpected = VALOR_DIARIA_DIAS_UTEIS.multiply(diasUteis)
                .add(VALOR_DIARIA_FINAIS_SEMANA.multiply(finaisDeSemana));
        assertThat(reserva.getValor()).isEqualTo(valorExpected);
    }

    @Test
    @DisplayName("Dado uma reserva válida, quando calcular o valor de uma reserva de mais de uma pessoa, então deve somar corretamente os valores das diárias")
    void dadoReservaValida_quandoCalcularValor_entaoDeveSomarCorretamenteComMaisPEssoas() {
        var reserva = ReservaEntity.builder()
                .dataEntrada(LocalDate.of(2025, 3, 3))
                .dataSaida(LocalDate.of(2025, 3, 9))
                .necessitaEstacionamento(false)
                .numeroPessoas((short) 2)
                .build();

        reservaVisitor.visitBeforeCreate(reserva);

        var diasUteis = BigDecimal.valueOf(5);
        var finaisDeSemana = BigDecimal.valueOf(2);
        var numeroPessoas = BigDecimal.valueOf(2);
        var valorExpected = VALOR_DIARIA_DIAS_UTEIS.multiply(diasUteis)
                .add(VALOR_DIARIA_FINAIS_SEMANA.multiply(finaisDeSemana))
                .multiply(numeroPessoas);
        assertThat(reserva.getValor()).isEqualTo(valorExpected);
    }

    @Test
    @DisplayName("Dado um check-in antes do horário mínimo, quando validar, então deve lançar exceção")
    void dadoCheckinAntesHorarioMinimo_quandoValidar_entaoDeveLancarExcecao() {
        var reserva = ReservaEntity.builder()
                .checkIn(LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 59))) // Antes das 14h00
                .build();

        assertThatThrownBy(() -> reservaVisitor.visitBeforeUpdate(reserva))
                .isInstanceOf(ReservaException.class)
                .hasMessageContaining("Check-in inválido! Deve ser hoje a partir das '14:00' horas.");
    }

    @Test
    @DisplayName("Dado um check-in, quando estiver exatamente no valor minimo, então não deve lançar exceção")
    void dadoCheckinNoHorarioMinimo_quandoValidar_entaoNaoDeveLancarExcecao() {
        var reserva = ReservaEntity.builder()
                .checkIn(LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 0))) // Exatamente às 14h00
                .build();

        assertThatCode(() -> reservaVisitor.visitBeforeUpdate(reserva))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Dado um checkout após o horário máximo, quando validar, então deve aplicar multa de 50%")
    void dadoCheckoutAposHorarioMaximo_quandoValidar_entaoDeveAplicarMulta() {
        var reserva = ReservaEntity.builder()
                .checkOut(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30))) // Após 12h00
                .build();

        reservaVisitor.visitBeforeUpdate(reserva);

        assertThat(reserva.getValorMulta()).isEqualByComparingTo(BigDecimal.valueOf(60)); // 50% de 120.00
    }

    @Test
    @DisplayName("Dado um checkout dentro do horário permitido, quando validar, então não deve aplicar multa")
    void dadoCheckoutDentroDoHorarioPermitido_quandoValidar_entaoNaoDeveAplicarMulta() {
        var reserva = ReservaEntity.builder()
                .checkOut(LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 59))) // Antes das 12h00
                .build();

        reservaVisitor.visitBeforeUpdate(reserva);

        assertThat(reserva.getValorMulta()).isEqualByComparingTo(BigDecimal.ZERO);
    }
}
