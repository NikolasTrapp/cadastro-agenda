package br.com.trapp.cadastroagendabackend.mapper;

import br.com.trapp.cadastroagendabackend.domain.HospedeEntity;
import br.com.trapp.cadastroagendabackend.dto.HospedeDto;

public interface HospedeMapper {

    HospedeDto toDto(HospedeEntity entity);
    HospedeEntity toEntity(HospedeDto dto);

}
