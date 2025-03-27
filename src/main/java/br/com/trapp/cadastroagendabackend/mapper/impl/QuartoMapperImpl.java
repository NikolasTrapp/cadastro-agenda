package br.com.trapp.cadastroagendabackend.mapper.impl;

import br.com.trapp.cadastroagendabackend.domain.QuartoEntity;
import br.com.trapp.cadastroagendabackend.dto.QuartoDto;
import br.com.trapp.cadastroagendabackend.mapper.QuartoMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuartoMapperImpl implements QuartoMapper {

    private final ModelMapper modelMapper;


    @Override
    public QuartoDto toDto(QuartoEntity entity) {
        return modelMapper.map(entity, QuartoDto.class);
    }

    @Override
    public QuartoEntity toEntity(QuartoDto dto) {
        return modelMapper.map(dto, QuartoEntity.class);
    }
}
