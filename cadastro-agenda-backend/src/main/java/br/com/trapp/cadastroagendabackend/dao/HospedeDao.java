package br.com.trapp.cadastroagendabackend.dao;

import br.com.trapp.cadastroagendabackend.controller.payload.HospedePendenteDto;
import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import br.com.trapp.cadastroagendabackend.dto.HospedeProjecao;
import br.com.trapp.cadastroagendabackend.util.FiltrosBusca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HospedeDao {

    List<HospedeProjecao> buscarPorNome(String nome);

    Page<HospedePendenteDto> buscarHospedesPendentesCheckIn(Pageable pageable);

    Page<HospedePendenteDto> buscarHospedesPendentesCheckOut(Pageable pageable);

    Page<HospedeDto> list(Pageable pageable, FiltrosBusca filtros);
}
