package br.com.trapp.cadastroagendabackend.controller;

import br.com.trapp.cadastroagendabackend.dto.QuartoDto;
import br.com.trapp.cadastroagendabackend.service.QuartoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quarto")
public class QuartoController {

    private final QuartoService quartoService;

    @PostMapping("/registrar")
    public ResponseEntity<QuartoDto> registrar(@Valid @RequestBody QuartoDto quarto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quartoService.criarQuarto(quarto));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<QuartoDto> atualizar(@PathVariable UUID id, @Valid @RequestBody QuartoDto quartoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(quartoService.atualizarQuarto(id, quartoDto));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        quartoService.removerQuarto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<QuartoDto> buscarPorId(@PathVariable UUID id) {
        var quarto = quartoService.getQuartoPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(quarto);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<QuartoDto>> buscarPaginado(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(quartoService.buscaPaginada(pageable));
    }
}
