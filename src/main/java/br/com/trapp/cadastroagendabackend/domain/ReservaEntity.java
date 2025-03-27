package br.com.trapp.cadastroagendabackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "reservas")
public class ReservaEntity extends AuditingEntity {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "data_entrada", nullable = false, columnDefinition = "date")
    @NotNull(message = "{reserva.dataEntrada.notNull}")
    private LocalDate dataEntrada;

    @Column(name = "data_saida", nullable = false, columnDefinition = "date")
    @NotNull(message = "{reserva.dataSaida.notNull}")
    private LocalDate dataSaida;

    @FutureOrPresent(message = "{reserva.checkIn.futureOrPresent}")
    @Column(name = "check_in", columnDefinition = "timestamp")
    private LocalDateTime checkIn;

    @FutureOrPresent(message = "{reserva.checkOut.futureOrPresent}")
    @Column(name = "check_out", columnDefinition = "timestamp")
    private LocalDateTime checkOut;

    @Positive(message = "{quarto.numeroPessoas.positive}")
    @NotNull(message = "{reserva.numeroPessoas.notNull}")
    @Column(name = "numero_pessoas", nullable = false, columnDefinition = "smallint")
    private Short numeroPessoas;

    @Column(name = "necessita_estacionamento", columnDefinition = "boolean", nullable = false)
    @NotNull(message = "{reserva.necessitaEstacionamento.notNull}")
    private Boolean necessitaEstacionamento;

    @NotNull(message = "{reserva.valor.notNull}")
    @Positive(message = "{reserva.valor.positive}")
    @Column(name = "valor", columnDefinition = "numeric(19, 4)", nullable = false)
    private BigDecimal valor;

    @Positive(message = "{reserva.valorMulta.positive}")
    @Column(name = "valor_multa", columnDefinition = "numeric(19, 4)")
    private BigDecimal valorMulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "{reserva.hospede.notNull}")
    @JoinColumn(name = "hospede_id", nullable = false, columnDefinition = "uuid")
    private HospedeEntity hospede;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "{reserva.quarto.notNull}")
    @JoinColumn(name = "quarto_id", nullable = false, columnDefinition = "uuid")
    private QuartoEntity quarto;

}
