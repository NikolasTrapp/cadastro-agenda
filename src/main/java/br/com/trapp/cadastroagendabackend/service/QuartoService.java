package br.com.trapp.cadastroagendabackend.service;

import br.com.trapp.cadastroagendabackend.dto.QuartoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface QuartoService {

    QuartoDto criarQuarto(QuartoDto quarto);
    QuartoDto atualizarQuarto(UUID id, QuartoDto quarto);
    void removerQuarto(UUID id);
    QuartoDto buscarQuartoPorId(UUID id);
    QuartoDto getQuartoPorId(UUID id);
    Page<QuartoDto> buscaPaginada(Pageable pageable);
}
