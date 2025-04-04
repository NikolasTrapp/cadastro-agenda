package br.com.trapp.cadastroagendabackend.controller.payload;

import br.com.trapp.cadastroagendabackend.dto.ReservaDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NovaReservaInput {

    @Valid
    @NotNull(message = "{novaReserva.validacoes.reserva}")
    private NovaReservaDto reserva;
}
