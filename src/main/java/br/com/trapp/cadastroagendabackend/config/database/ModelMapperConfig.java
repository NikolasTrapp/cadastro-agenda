package br.com.trapp.cadastroagendabackend.config.database;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        //Customizacoes...

        return modelMapper;
    }
}
