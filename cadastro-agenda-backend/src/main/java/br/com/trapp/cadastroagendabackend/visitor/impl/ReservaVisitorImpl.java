package br.com.trapp.cadastroagendabackend.visitor.impl;

import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;
import br.com.trapp.cadastroagendabackend.exception.ReservaException;
import br.com.trapp.cadastroagendabackend.visitor.ReservaVisitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import static br.com.trapp.cadastroagendabackend.util.Utils.format;
import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservaVisitorImpl implements ReservaVisitor {

    public static final BigDecimal VALOR_DIARIA_DIAS_UTEIS = new BigDecimal("120.00");
    public static final BigDecimal VALOR_DIARIA_FINAIS_SEMANA = new BigDecimal("180.00");
    public static final BigDecimal VALOR_TAXA_ESTACIONAMENTO_DIAS_UTEIS = new BigDecimal("15.00");
    public static final BigDecimal VALOR_TAXA_ESTACIONAMENTO_FINAIS_SEMANA = new BigDecimal("20.00");

    public static final LocalTime HORARIO_MINIMO_CHECKIN = LocalTime.of(14, 0);
    public static final LocalTime HORARIO_MAXIMO_CHECKOUT = LocalTime.of(12, 0);


    @Override
    public void visitBeforeCreate(ReservaEntity entity) {
        contribute(entity);
    }

    private void contribute(ReservaEntity reserva) {
        var dataEntrada = reserva.getDataEntrada();
        var dataSaida = reserva.getDataSaida();
        var necessitaEstacionamento = reserva.isNecessitaEstacionamento();

        var valorTotal = BigDecimal.ZERO;
        var dataAtual = dataEntrada;

        log.info("Iniciando cálculo de valor da reserva de {} até {}. Necessita estacionamento: {}",
                dataEntrada, dataSaida, necessitaEstacionamento);

        while (!dataAtual.isAfter(dataSaida)) {
            var diaSemana = dataAtual.getDayOfWeek();
            var isFinalDeSemana = (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY);

            var valorDiaria = isFinalDeSemana ? VALOR_DIARIA_FINAIS_SEMANA : VALOR_DIARIA_DIAS_UTEIS;

            var taxaEstacionamento = BigDecimal.ZERO;
            if (Boolean.TRUE.equals(necessitaEstacionamento)) {
                taxaEstacionamento = taxaEstacionamento.add(isFinalDeSemana ?
                        VALOR_TAXA_ESTACIONAMENTO_FINAIS_SEMANA :
                        VALOR_TAXA_ESTACIONAMENTO_DIAS_UTEIS);
            }

            valorTotal = valorTotal.add(valorDiaria).add(taxaEstacionamento);

            log.info("Data: {} | Valor Diária: R$ {} | Taxa Estacionamento: R$ {} | Total acumulado: R$ {}",
                    dataAtual, valorDiaria, taxaEstacionamento, valorTotal);

            dataAtual = dataAtual.plusDays(1);
        }

        valorTotal = valorTotal.multiply(BigDecimal.valueOf(reserva.getNumeroPessoas()));
        reserva.setValor(valorTotal);
        log.info("Valor final da reserva: R$ {}", valorTotal);
    }

    @Override
    public void visitBeforeUpdate(ReservaEntity entity) {
        validarCheckInCheckOut(entity);
    }

    private void validarCheckInCheckOut(ReservaEntity reserva) {
        var checkIn = reserva.getCheckIn();
        var checkOut = reserva.getCheckOut();

        log.info("Validando horários de check-in e check-out...");

        var horarioCheckinMinimo = LocalDate.now().atTime(HORARIO_MINIMO_CHECKIN);
        if (nonNull(checkIn) && checkIn.isBefore(horarioCheckinMinimo)) {
            log.error("Check-in inválido! Data/Hora fornecida: {} - Deve ser hoje a partir das {}",
                    checkIn, HORARIO_MINIMO_CHECKIN);
            throw new ReservaException(format("Check-in inválido! Deve ser hoje a partir das '{}' horas.", HORARIO_MINIMO_CHECKIN));
        }

        if (nonNull(checkOut) && checkOut.toLocalTime().isAfter(HORARIO_MAXIMO_CHECKOUT)) {
            var diaSemana = checkOut.getDayOfWeek();
            var isFinalDeSemana = (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY);
            var valorDiaria = isFinalDeSemana ? VALOR_DIARIA_FINAIS_SEMANA : VALOR_DIARIA_DIAS_UTEIS;
            var taxaExtra = valorDiaria.multiply(BigDecimal.valueOf(0.5D)); // 50% do valor da diária

            reserva.setValorMulta(taxaExtra);
            log.warn("Check-out da reserva: '{}' após as 12:00 - Multa de 50% aplicada: R$ {}",
                    reserva.getId(), taxaExtra);
            return;
        }
        reserva.setValorMulta(BigDecimal.ZERO);
    }
}
