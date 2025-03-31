package br.com.trapp.cadastroagendabackend.dao;

import br.com.trapp.cadastroagendabackend.dto.QuartoProjecao;

import java.util.List;

public interface QuartoDao {

    List<QuartoProjecao> buscarQuartos(Long numeroQuarto);
}
