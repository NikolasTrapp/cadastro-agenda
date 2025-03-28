package br.com.trapp.cadastroagendabackend.controller.payload;

import br.com.trapp.cadastroagendabackend.dto.ReservaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EfetuarCheckoutOutput {

    private ReservaDto reserva;
}
