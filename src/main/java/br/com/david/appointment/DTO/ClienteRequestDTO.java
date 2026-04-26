package br.com.david.appointment.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(@NotBlank(message = "Nome do cliente obrigatorio")@Size(max = 150) String nome,
                                @NotBlank(message = "Telefone é obrigatorio")@Pattern(
                                        regexp = "^(?!([0-9])\\1{10})\\d{10,11}$",
                                        message = "Telefone deve conter 10 ou 11 dígitos válidos"
                                ) String telefone) {
}
