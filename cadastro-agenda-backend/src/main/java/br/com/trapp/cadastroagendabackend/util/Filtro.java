package br.com.trapp.cadastroagendabackend.util;

import br.com.trapp.cadastroagendabackend.enums.Operacao;
import lombok.Getter;

@Getter
public class Filtro {

    private String campo;
    private String valor;
    private Operacao operacao;

}
