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
public class NovaReservaOutput {

    @Valid
    @NotNull(message = "{novaReserva.validacoes.reserva}")
    private ReservaDto reserva;

}
