package br.com.trapp.cadastroagendabackend.controller.payload;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospedePendenteDto {

    private UUID id;

    @NotBlank(message = "{hospede.nome.notBlank}")
    private String nome;

    @NotBlank(message = "{hospede.documento.notBlank}")
    private String documento;

    @NotBlank(message = "{hospede.telefone.notBlank}")
    private String telefone;

    @Past(message = "{hospede.dataNascimento.past}")
    private LocalDate dataNascimento;

    //Reserva
    @FutureOrPresent(message = "{reserva.checkIn.futureOrPresent}")
    private LocalDateTime checkIn;

    @FutureOrPresent(message = "{reserva.checkOut.futureOrPresent}")
    private LocalDateTime checkOut;
}
