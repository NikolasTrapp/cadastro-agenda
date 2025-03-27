package br.com.trapp.cadastroagendabackend.config.database;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Validated
@Component
@ConfigurationProperties("cadastro.agenda")
public class CadastroAgendaProperties {

    @Valid
    @NotNull
    private DatabaseProperties database;

    @Setter
    @Getter
    @Validated
    public static class DatabaseProperties {

        @NotBlank(message = "{dbUrl.notBlank}")
        private String url;

        @NotBlank(message = "{dbUsername.notBlank}")
        private String username;

        @NotBlank(message = "{dbPassword.notBlank}")
        private String password;

        @NotBlank(message = "{dbDriverClassName.notBlank}")
        private String driverClassName;

        private int maximumPoolSize = 10;
        private int minimumIdle = 5;
        private long connectionTimeout = 30_000;
        private long idleTimeout = 600_000;
        private long maxLifetime = 1_800_000;

        private String flywayLocations = "classpath:db/migration";
        private boolean flywayEnabled = true;
    }
}
