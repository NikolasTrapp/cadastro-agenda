package br.com.trapp.cadastroagendabackend.visitor.impl;

import br.com.trapp.cadastroagendabackend.domain.HospedeEntity;
import br.com.trapp.cadastroagendabackend.visitor.HospedeVisitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HospedeVisitorImpl implements HospedeVisitor {

    @Override
    public void validar(HospedeEntity entity) {

    }
}
