package br.com.trapp.cadastroagendabackend.service.impl;

import br.com.trapp.cadastroagendabackend.controller.payload.HospedePendenteDto;
import br.com.trapp.cadastroagendabackend.dao.HospedeDao;
import br.com.trapp.cadastroagendabackend.domain.HospedeEntity;
import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import br.com.trapp.cadastroagendabackend.dto.HospedeProjecao;
import br.com.trapp.cadastroagendabackend.mapper.HospedeMapper;
import br.com.trapp.cadastroagendabackend.repository.HospedeRepository;
import br.com.trapp.cadastroagendabackend.service.HospedeService;
import br.com.trapp.cadastroagendabackend.util.FiltrosBusca;
import br.com.trapp.cadastroagendabackend.visitor.HospedeVisitor;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static br.com.trapp.cadastroagendabackend.util.Utils.format;

@Slf4j
@Service
@RequiredArgsConstructor
public class HospedeServiceImpl implements HospedeService {

    private final HospedeRepository hospedeRepository;
    private final HospedeDao hospedeDao;
    private final HospedeVisitor hospedeVisitor;
    private final HospedeMapper hospedeMapper;

    @Override
    @Transactional
    public HospedeDto create(HospedeDto dto) {
        var hospedeEntity = hospedeMapper.toEntity(dto);
        hospedeVisitor.visitBeforeCreate(hospedeEntity);
        var hospede = hospedeRepository.save(hospedeEntity);
        hospedeVisitor.visitAfterCreate(hospede);
        return hospedeMapper.toDto(hospede);
    }

    @Override
    @Transactional
    public HospedeDto update(UUID id, HospedeDto dto) {
        var hospede = getHospedeEntityPorId(id); //NOSONAR ja tem transacao e nao faz commit

        atualizarHospede(hospede, dto);
        hospedeVisitor.visitBeforeUpdate(hospede);

        hospede = hospedeRepository.save(hospede);

        hospedeVisitor.visitAfterUpdate(hospede);
        return hospedeMapper.toDto(hospede);
    }

    @Override
    @Transactional(readOnly = true)
    public HospedeDto getById(UUID id) {
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
    public void deleteById(UUID id) {
        hospedeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public HospedeDto findById(UUID id) {
        return hospedeRepository.findById(id)
                .map(hospedeMapper::toDto)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HospedeDto> list(Pageable pageable, FiltrosBusca filtros) {
        return hospedeDao.list(pageable, filtros);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospedeProjecao> buscarHospedes(String query) {
        return hospedeDao.buscarPorNome(query);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HospedePendenteDto> buscarHospedesPendentesCheckIn(Pageable pageable) {
        return hospedeDao.buscarHospedesPendentesCheckIn(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HospedePendenteDto> buscarHospedesPendentesCheckOut(Pageable pageable) {
        return hospedeDao.buscarHospedesPendentesCheckOut(pageable);
    }

    private void atualizarHospede(HospedeEntity entity, HospedeDto dto) {
        entity.setNome(dto.getNome());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setDocumento(dto.getDocumento());
        entity.setTelefone(dto.getTelefone());
    }
}
