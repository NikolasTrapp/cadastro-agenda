package br.com.trapp.cadastroagendabackend.mapper;

import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;
import br.com.trapp.cadastroagendabackend.dto.ReservaDto;

public interface ReservaMapper {

    ReservaDto toDto(ReservaEntity entity);

    ReservaEntity toEntity(ReservaDto dto);

}
