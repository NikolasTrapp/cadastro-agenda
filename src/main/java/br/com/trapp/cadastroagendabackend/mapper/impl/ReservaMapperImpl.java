package br.com.trapp.cadastroagendabackend.mapper.impl;

import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;
import br.com.trapp.cadastroagendabackend.dto.ReservaDto;
import br.com.trapp.cadastroagendabackend.mapper.ReservaMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservaMapperImpl implements ReservaMapper {

    private final ModelMapper modelMapper;


    @Override
    public ReservaDto toDto(ReservaEntity entity) {
        return modelMapper.map(entity, ReservaDto.class);
    }

    @Override
    public ReservaEntity toEntity(ReservaDto dto) {
        return modelMapper.map(dto, ReservaEntity.class);
    }
}
