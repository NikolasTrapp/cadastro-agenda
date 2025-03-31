package br.com.trapp.cadastroagendabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuartoProjecao {

    private UUID id;
    private Long numeroQuarto;

}
