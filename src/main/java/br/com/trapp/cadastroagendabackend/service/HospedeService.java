package br.com.trapp.cadastroagendabackend.service;

import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HospedeService {

    HospedeDto create(HospedeDto hospede);
    HospedeDto update(UUID id, HospedeDto hospede);
    void deleteById(UUID id);
    HospedeDto findById(UUID id);
    HospedeDto getById(UUID id);
    Page<HospedeDto> list(Pageable pageable);
}
