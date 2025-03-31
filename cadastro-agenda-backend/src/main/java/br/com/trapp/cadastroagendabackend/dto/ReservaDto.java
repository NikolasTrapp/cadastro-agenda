package br.com.trapp.cadastroagendabackend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDto {

    private UUID id;

    @NotNull(message = "{reserva.dataEntrada.notNull}")
    private LocalDate dataEntrada;

    @NotNull(message = "{reserva.dataSaida.notNull}")
    private LocalDate dataSaida;

    @FutureOrPresent(message = "{reserva.checkIn.futureOrPresent}")
    private LocalDateTime checkIn;

    @FutureOrPresent(message = "{reserva.checkOut.futureOrPresent}")
    private LocalDateTime checkOut;

    @Positive(message = "{quarto.numeroPessoas.positive}")
    @NotNull(message = "{reserva.numeroPessoas.notNull}")
    private Short numeroPessoas;

    @NotNull(message = "{reserva.necessitaEstacionamento.notNull}")
    private Boolean necessitaEstacionamento;

    @Positive(message = "{reserva.valor.positive}")
    private BigDecimal valor;

    @Positive(message = "{reserva.valorMulta.positive}")
    private BigDecimal valorMulta;

    @Valid
    @NotNull(message = "{reserva.hospede.notNull}")
    private HospedeDto hospede;

    @Valid
    @NotNull(message = "{reserva.quarto.notNull}")
    private QuartoDto quarto;

}
