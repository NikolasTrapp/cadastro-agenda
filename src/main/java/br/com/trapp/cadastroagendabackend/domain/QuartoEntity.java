package br.com.trapp.cadastroagendabackend.domain;

import br.com.trapp.cadastroagendabackend.enums.TipoQuarto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "quartos")
public class QuartoEntity extends AuditingEntity {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @Positive(message = "{quarto.numeroQuarto.positive}")
    @NotNull(message = "{quarto.numeroQuarto.notNull}")
    @Column(name="numero_quarto", nullable = false, unique = true, columnDefinition = "BIGINT")
    private Long numeroQuarto;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{quarto.tipoQuarto.notNull}")
    @Column(name="tipo_quarto", nullable = false, columnDefinition = "varchar")
    private TipoQuarto tipoQuarto;

    @OneToMany(mappedBy = "quarto", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ReservaEntity> reservas;
}
