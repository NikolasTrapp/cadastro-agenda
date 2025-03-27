package br.com.trapp.cadastroagendabackend.visitor;

import br.com.trapp.cadastroagendabackend.domain.HospedeEntity;

public interface HospedeVisitor {

    void validar(HospedeEntity entity);

}
