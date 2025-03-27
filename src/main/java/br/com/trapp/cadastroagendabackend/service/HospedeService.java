package br.com.trapp.cadastroagendabackend.service;

import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HospedeService {

    HospedeDto criarHospede(HospedeDto hospede);
    HospedeDto atualizarHospede(UUID id, HospedeDto hospede);
    void removerHospede(UUID id);
    HospedeDto buscarHospedePorId(UUID id);
    HospedeDto getHospedePorId(UUID id);
    Page<HospedeDto> buscaPaginada(Pageable pageable);
}
