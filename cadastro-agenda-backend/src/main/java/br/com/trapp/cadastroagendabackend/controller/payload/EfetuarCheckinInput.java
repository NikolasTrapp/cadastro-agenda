package br.com.trapp.cadastroagendabackend.controller.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EfetuarCheckinInput {

    @NotNull(message = "{efetuarCheckin.validacoes.reservaId}")
    private UUID reservaId;
}
