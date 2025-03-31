package br.com.trapp.cadastroagendabackend.dto;

import br.com.trapp.cadastroagendabackend.enums.TipoQuarto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuartoDto {

    private UUID id;

    @NotNull(message = "{quarto.numeroQuarto.notNull}")
    @Positive(message = "{quarto.numeroPessoas.positive}")
    private Long numeroQuarto;

    @NotNull(message = "{quarto.tipoQuarto.notNull}")
    private TipoQuarto tipoQuarto;

}
