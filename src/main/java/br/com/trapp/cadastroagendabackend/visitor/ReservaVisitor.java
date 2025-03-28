package br.com.trapp.cadastroagendabackend.visitor;

import br.com.trapp.cadastroagendabackend.domain.ReservaEntity;

public interface ReservaVisitor {

    default void visitBeforeCreate(ReservaEntity entity) {

    }

    default void visitAfterCreate(ReservaEntity entity) {

    }

    default void visitBeforeUpdate(ReservaEntity entity) {

    }

    default void visitAfterUpdate(ReservaEntity entity) {

    }

}
