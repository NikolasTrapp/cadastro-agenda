package br.com.trapp.cadastroagendabackend.controller.payload;

import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import br.com.trapp.cadastroagendabackend.dto.QuartoDto;
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
public class NovaReservaDto {

    private UUID id;

    @NotNull(message = "{reserva.dataEntrada.notNull}")
    private LocalDate dataEntrada;

    @NotNull(message = "{reserva.dataSaida.notNull}")
    private LocalDate dataSaida;

    @Positive(message = "{quarto.numeroPessoas.positive}")
    @NotNull(message = "{reserva.numeroPessoas.notNull}")
    private Short numeroPessoas;

    @NotNull(message = "{reserva.necessitaEstacionamento.notNull}")
    private Boolean necessitaEstacionamento;

    @NotNull(message = "{reserva.hospede.notNull}")
    private HospedeDto hospede;

    @NotNull(message = "{reserva.quarto.notNull}")
    private QuartoDto quarto;
}
