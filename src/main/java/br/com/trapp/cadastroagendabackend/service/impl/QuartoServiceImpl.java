package br.com.trapp.cadastroagendabackend.service.impl;

import br.com.trapp.cadastroagendabackend.domain.QuartoEntity;
import br.com.trapp.cadastroagendabackend.dto.QuartoDto;
import br.com.trapp.cadastroagendabackend.mapper.QuartoMapper;
import br.com.trapp.cadastroagendabackend.repository.QuartoRepository;
import br.com.trapp.cadastroagendabackend.service.QuartoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static br.com.trapp.cadastroagendabackend.util.Utils.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuartoServiceImpl implements QuartoService {

    private final QuartoRepository quartoRepository;
    private final QuartoMapper quartoMapper;

    @Override
    @Transactional
    public QuartoDto criarQuarto(QuartoDto dto) {
        var quartoEntity = quartoMapper.toEntity(dto);
        var quarto = quartoRepository.save(quartoEntity);
        return quartoMapper.toDto(quarto);
    }

    @Override
    @Transactional
    public QuartoDto atualizarQuarto(UUID id, QuartoDto dto) {
        var quarto = getQuartoEntityPorId(id); //NOSONAR
        atualizarQuarto(quarto, dto);
        quarto = quartoRepository.save(quarto);
        return quartoMapper.toDto(quarto);
    }

    @Override
    @Transactional(readOnly = true)
    public QuartoDto getQuartoPorId(UUID id) {
        return quartoRepository.findById(id)
                .map(quartoMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format("Quarto com identificador '{}' não encontrado.", id)));
    }

    @Transactional(readOnly = true)
    public QuartoEntity getQuartoEntityPorId(UUID id) {
        return quartoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Quarto com identificador '{}' não encontrado.", id)));
    }

    @Override
    @Transactional
    public void removerQuarto(UUID id) {
        quartoRepository.deleteById(id);
    }

    @Override
    public QuartoDto buscarQuartoPorId(UUID id) {
        return quartoRepository.findById(id)
                .map(quartoMapper::toDto)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuartoDto> buscaPaginada(Pageable pageable) {
        return quartoRepository.findAll(pageable).map(quartoMapper::toDto);
    }

    private void atualizarQuarto(QuartoEntity entity, QuartoDto dto) {
        entity.setNumeroQuarto(dto.getNumeroQuarto());
        entity.setTipoQuarto(dto.getTipoQuarto());
    }
}
