package br.com.trapp.cadastroagendabackend.controller;

import br.com.trapp.cadastroagendabackend.controller.payload.HospedePendenteDto;
import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import br.com.trapp.cadastroagendabackend.dto.HospedeProjecao;
import br.com.trapp.cadastroagendabackend.service.HospedeService;
import br.com.trapp.cadastroagendabackend.util.FiltrosBusca;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hospede")
public class HospedeController {

    private final HospedeService hospedeService;

    @PostMapping("/registrar")
    public ResponseEntity<HospedeDto> registrar(@Valid @RequestBody HospedeDto hospede) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospedeService.create(hospede));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<HospedeDto> atualizar(@PathVariable UUID id, @Valid @RequestBody HospedeDto hospedeDto) {
        return ResponseEntity.status(HttpStatus.OK).body(hospedeService.update(id, hospedeDto));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        hospedeService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<HospedeDto> buscarPorId(@PathVariable UUID id) {
        var hospede = hospedeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(hospede);
    }

    @PostMapping("/listar")
    public ResponseEntity<Page<HospedeDto>> buscarPaginado(Pageable pageable, @RequestBody FiltrosBusca filtros) {
        return ResponseEntity.status(HttpStatus.OK).body(hospedeService.list(pageable, filtros));
    }

    @GetMapping("/buscarHospedes")
    public ResponseEntity<List<HospedeProjecao>> buscarHospedes(@RequestParam(name = "nome") String nome) {
        return ResponseEntity.status(HttpStatus.OK).body(hospedeService.buscarHospedes(nome));
    }

    @GetMapping("/buscarHospedesPendentesCheckIn")
    public ResponseEntity<Page<HospedePendenteDto>> buscarHospedesPendentesCheckIn(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(hospedeService.buscarHospedesPendentesCheckIn(pageable));
    }

    @GetMapping("/buscarHospedesPendentesCheckOut")
    public ResponseEntity<Page<HospedePendenteDto>> buscarHospedesPendentesCheckOut(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(hospedeService.buscarHospedesPendentesCheckOut(pageable));
    }
}
