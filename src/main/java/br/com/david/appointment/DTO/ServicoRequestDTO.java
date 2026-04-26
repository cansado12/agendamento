package br.com.david.appointment.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ServicoRequestDTO(@NotBlank(message = "Nome do servico obrigatorio")@Size(max = 150) String nome,
                                @NotNull(message = "Corte deve ter duraçao")@Min(value = 1, message = "Duração deve ser maior que 0") Integer duracao,
                                @NotNull(message = "Preco é obrigatorio") @DecimalMin(value = "0.0",inclusive = false, message = "Preco deve ser maior que zero")
                                BigDecimal preco,
                                boolean ativo) {
}
