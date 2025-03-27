package br.com.trapp.cadastroagendabackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "propriedades")
public class PropriedadeEntity extends AuditingEntity {

    @Id
    @Getter
    @Setter
    @Column(name = "id", unique = true, updatable = false, columnDefinition = "varchar")
    private String id;

    @Column(name = "valor", nullable = false, columnDefinition = "varchar")
    @NotNull(message = "{propriedade.valor.notNull}")
    private String valor;


}
