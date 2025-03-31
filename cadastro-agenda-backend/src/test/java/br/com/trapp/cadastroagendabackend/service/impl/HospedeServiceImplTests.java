package br.com.trapp.cadastroagendabackend.service.impl;

import br.com.trapp.cadastroagendabackend.dto.HospedeDto;
import br.com.trapp.cadastroagendabackend.mapper.impl.HospedeMapperImpl;
import br.com.trapp.cadastroagendabackend.service.HospedeService;
import br.com.trapp.cadastroagendabackend.visitor.HospedeVisitor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;

import static br.com.trapp.cadastroagendabackend.util.Constantes.PACKAGE;
import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EntityScan(PACKAGE)
@SpringJUnitConfig({
        ModelMapper.class,
        HospedeMapperImpl.class,
        HospedeServiceImpl.class
})
@EnableJpaRepositories(PACKAGE)
@TestPropertySource("/test.properties")
@ExtendWith(MockitoExtension.class)
class HospedeServiceImplTests {

    @MockitoBean
    HospedeVisitor hospedeVisitor;

    @Autowired
    HospedeService hospedeService;

    @Test
    @DisplayName("Dado um HospedeDto válido, quando criar hospede, então o hospede deve ser criado corretamente")
    void testarCriacaoHospede() {
        var hospedeDto = HospedeDto.builder()
                .nome("João Silva")
                .documento("12345678901234")
                .telefone("11987654321")
                .dataNascimento(LocalDate.of(1990, 5, 10))
                .build();

        var hospedeCriado = hospedeService.create(hospedeDto);

        assertThat(hospedeCriado)
                .isNotNull()
                .extracting(
                        it -> isNull(it.getId()),
                        HospedeDto::getNome,
                        HospedeDto::getDocumento,
                        HospedeDto::getTelefone,
                        HospedeDto::getDataNascimento
                ).containsExactly(
                        false,
                        "João Silva",
                        "12345678901234",
                        "11987654321",
                        LocalDate.of(1990, 5, 10)
                );
    }

}
