package br.com.trapp.cadastroagendabackend.controller;

import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import br.com.trapp.cadastroagendabackend.service.HospedeService;
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
@RequestMapping("/api/v1/hospede")
public class HospedeController {

    private final HospedeService hospedeService;

    @PostMapping(name = "/registrar")
    public ResponseEntity<HospedeDto> registrar(@Valid @RequestBody HospedeDto hospede) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospedeService.criarHospede(hospede));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<HospedeDto> atualizar(@PathVariable UUID id, @Valid @RequestBody HospedeDto hospedeDto) {
        return ResponseEntity.status(HttpStatus.OK).body(hospedeService.atualizarHospede(id, hospedeDto));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        hospedeService.removerHospede(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<HospedeDto> buscarPorId(@PathVariable UUID id) {
        var hospede = hospedeService.getHospedePorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(hospede);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<HospedeDto>> buscarPaginado(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(hospedeService.buscaPaginada(pageable));
    }
}
