package br.com.trapp.cadastroagendabackend.visitor;

import br.com.trapp.cadastroagendabackend.domain.HospedeEntity;

public interface HospedeVisitor {

    default void visitBeforeCreate(HospedeEntity entity) {

    }

    default void visitAfterCreate(HospedeEntity entity) {

    }

    default void visitBeforeUpdate(HospedeEntity entity) {

    }

    default void visitAfterUpdate(HospedeEntity entity) {

    }

}
