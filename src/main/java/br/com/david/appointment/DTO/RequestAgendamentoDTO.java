package br.com.david.appointment.DTO;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RequestAgendamentoDTO(@NotNull(message = "Data de inicio é obrigatoria") LocalDateTime inicio,
                                    @NotNull(message = "Agendamento precisa ter cliente") Long clienteId,
                                    @NotNull(message = "Agendamento precisa descricao de servico") Long servicoId) {
}
