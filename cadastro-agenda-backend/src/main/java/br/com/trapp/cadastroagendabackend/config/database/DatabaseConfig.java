package br.com.trapp.cadastroagendabackend.config.database;

import br.com.trapp.cadastroagendabackend.config.CadastroAgendaProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class DatabaseConfig {

    private final CadastroAgendaProperties properties;

    @Bean
    DataSource dataSource() {
        var props = properties.getDatabase();
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(props.getUrl());
        hikariConfig.setUsername(props.getUsername());
        hikariConfig.setPassword(props.getPassword());
        hikariConfig.setDriverClassName(props.getDriverClassName());

        hikariConfig.setMaximumPoolSize(props.getMaximumPoolSize());
        hikariConfig.setMinimumIdle(props.getMinimumIdle());
        hikariConfig.setConnectionTimeout(props.getConnectionTimeout());
        hikariConfig.setIdleTimeout(props.getIdleTimeout());
        hikariConfig.setMaxLifetime(props.getMaxLifetime());

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    Flyway flyway(DataSource dataSource) {
        var props = properties.getDatabase();
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(props.getFlywayLocations())
                .baselineOnMigrate(true)
                .load();

        if (props.isFlywayEnabled()) {
            flyway.migrate();
        }
        return flyway;
    }

    @Bean
    PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
