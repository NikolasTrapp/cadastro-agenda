package br.com.trapp.cadastroagendabackend.visitor.impl;

import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;
import br.com.trapp.cadastroagendabackend.visitor.ReservaVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservaVisitorImpl implements ReservaVisitor {


    @Override
    public void validate(ReservaEntity reserva) {

    }

    @Override
    public ReservaEntity beforeCreate(ReservaEntity entity) {
        return null;
    }
}
