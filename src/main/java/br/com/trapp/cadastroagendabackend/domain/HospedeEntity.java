package br.com.trapp.cadastroagendabackend.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "hospedes")
public class HospedeEntity extends AuditingEntity {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "nome", nullable = false, columnDefinition = "varchar")
    @NotBlank(message = "{hospede.nome.notBlank}")
    private String nome;

    @Column(name = "documento", nullable = false, unique = true, columnDefinition = "varchar", length = 14)
    @NotBlank(message = "{hospede.documento.notBlank}")
    private String documento;

    @Column(name = "telefone", nullable = false, unique = true, length = 19, columnDefinition = "varchar")
    @NotBlank(message = "{hospede.telefone.notBlank}")
    private String telefone;

    @Past(message = "{hospede.dataNascimento.past}")
    @Column(name= "data_nascimento", columnDefinition = "date")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "hospede", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ReservaEntity> reservas = new HashSet<>();

}
