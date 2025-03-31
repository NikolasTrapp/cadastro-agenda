package br.com.trapp.cadastroagendabackend.dao;

import br.com.trapp.cadastroagendabackend.dto.ReservaDto;
import br.com.trapp.cadastroagendabackend.util.FiltrosBusca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservaDao {

    Page<ReservaDto> list(Pageable pageable, FiltrosBusca filtros);
}
