package br.com.trapp.cadastroagendabackend.mapper.impl;

import br.com.trapp.cadastroagendabackend.domain.HospedeEntity;
import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import br.com.trapp.cadastroagendabackend.mapper.HospedeMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HospedeMapperImpl implements HospedeMapper {

    private final ModelMapper modelMapper;


    @Override
    public HospedeDto toDto(HospedeEntity entity) {
        return modelMapper.map(entity, HospedeDto.class);
    }

    @Override
    public HospedeEntity toEntity(HospedeDto dto) {
        return modelMapper.map(dto, HospedeEntity.class);
    }
}
