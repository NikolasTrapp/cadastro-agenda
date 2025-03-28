package br.com.trapp.cadastroagendabackend.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UtilsTests {

    @Test
    @DisplayName("Dado um formato e argumentos, quando formatar, então deve retornar a string formatada corretamente")
    void dadoFormatoEArgumentos_quandoFormatar_entaoDeveRetornarStringFormatada() {
        var result = Utils.format("Olá, meu nome é {} e tenho {} anos", "Nikolas", 20);

        assertThat(result).isEqualTo("Olá, meu nome é Nikolas e tenho 20 anos");
    }

    @Test
    @DisplayName("Dado uma string com valor nulo, quando toStrSafe for chamado, então deve retornar null")
    void dadoObjetoNulo_quandoToStrSafeForChamado_entaoDeveRetornarNull() {
        var result = Utils.toStrSafe(null);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Dado um objeto não nulo, quando toStrSafe for chamado, então deve retornar a string do objeto")
    void dadoObjetoNaoNulo_quandoToStrSafeForChamado_entaoDeveRetornarStringDoObjeto() {
        var result = Utils.toStrSafe(123);

        assertThat(result).isEqualTo("123");
    }
}
