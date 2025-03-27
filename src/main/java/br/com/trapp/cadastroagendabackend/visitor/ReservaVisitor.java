package br.com.trapp.cadastroagendabackend.visitor;

import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;

public interface ReservaVisitor {

    void validate(ReservaEntity reserva);
    ReservaEntity beforeCreate(ReservaEntity entity);

}
