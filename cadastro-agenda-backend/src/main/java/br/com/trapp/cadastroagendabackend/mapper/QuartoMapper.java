package br.com.trapp.cadastroagendabackend.mapper;

import br.com.trapp.cadastroagendabackend.domain.QuartoEntity;
import br.com.trapp.cadastroagendabackend.dto.QuartoDto;

public interface QuartoMapper {

    QuartoDto toDto(QuartoEntity entity);
    QuartoEntity toEntity(QuartoDto dto);

}
