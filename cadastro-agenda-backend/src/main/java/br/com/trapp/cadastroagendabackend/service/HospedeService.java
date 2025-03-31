package br.com.trapp.cadastroagendabackend.service;

import br.com.trapp.cadastroagendabackend.controller.payload.HospedePendenteDto;
import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import br.com.trapp.cadastroagendabackend.dto.HospedeProjecao;
import br.com.trapp.cadastroagendabackend.util.FiltrosBusca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface HospedeService {

    HospedeDto create(HospedeDto hospede);
    HospedeDto update(UUID id, HospedeDto hospede);
    void deleteById(UUID id);
    HospedeDto findById(UUID id);
    HospedeDto getById(UUID id);
    Page<HospedeDto> list(Pageable pageable, FiltrosBusca filtros);

    List<HospedeProjecao> buscarHospedes(String query);
    Page<HospedePendenteDto> buscarHospedesPendentesCheckIn(Pageable pageable);
    Page<HospedePendenteDto> buscarHospedesPendentesCheckOut(Pageable pageable);
}
