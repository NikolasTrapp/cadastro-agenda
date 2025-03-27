package br.com.trapp.cadastroagendabackend.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("appVersion")
public class ActuatorAppVersion implements HealthIndicator {

    @Override
    public Health getHealth(boolean includeDetails) {
        return health();
    }

    @Override
    public Health health() {
        var status = Health.up();
        return status
                .withDetail("appVersion", "0.0.1")
                .build();
    }
}
