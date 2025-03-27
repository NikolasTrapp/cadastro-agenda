package br.com.trapp.cadastroagendabackend.service.impl;

import br.com.trapp.cadastroagendabackend.domain.HospedeEntity;
import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import br.com.trapp.cadastroagendabackend.mapper.HospedeMapper;
import br.com.trapp.cadastroagendabackend.repository.HospedeRepository;
import br.com.trapp.cadastroagendabackend.service.HospedeService;
import br.com.trapp.cadastroagendabackend.visitor.HospedeVisitor;
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
public class HospedeServiceImpl implements HospedeService {

    private final HospedeRepository hospedeRepository;
    private final HospedeVisitor hospedeVisitor;
    private final HospedeMapper hospedeMapper;

    @Override
    @Transactional
    public HospedeDto criarHospede(HospedeDto dto) {
        var hospedeEntity = hospedeMapper.toEntity(dto);
        hospedeVisitor.validar(hospedeEntity);
        var hospede = hospedeRepository.save(hospedeEntity);
        return hospedeMapper.toDto(hospede);
    }

    @Override
    @Transactional
    public HospedeDto atualizarHospede(UUID id, HospedeDto dto) {
        var hospede = getHospedeEntityPorId(id); //NOSONAR ja tem transacao e nao faz commit
        atualizarHospede(hospede, dto);
        hospedeVisitor.validar(hospede);
        hospede = hospedeRepository.save(hospede);
        return hospedeMapper.toDto(hospede);
    }

    @Override
    @Transactional(readOnly = true)
    public HospedeDto getHospedePorId(UUID id) {
        return hospedeRepository.findById(id).
                map(hospedeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format("Hospede com identificador '{}' nao encontrado.", id)));
    }

    @Transactional(readOnly = true)
    public HospedeEntity getHospedeEntityPorId(UUID id) {
        return hospedeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Hospede com identificador '{}' nao encontrado.", id)));
    }

    @Override
    @Transactional
    public void removerHospede(UUID id) {
        hospedeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public HospedeDto buscarHospedePorId(UUID id) {
        return hospedeRepository.findById(id)
                .map(hospedeMapper::toDto)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HospedeDto> buscaPaginada(Pageable pageable) {
        return null;
    }

    private void atualizarHospede(HospedeEntity entity, HospedeDto dto) {
        entity.setNome(dto.getNome());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setDocumento(dto.getDocumento());
        entity.setTelefone(dto.getTelefone());
    }
}
