package br.com.david.appointment.DTO;

import br.com.david.appointment.model.Status;

import java.time.LocalDateTime;

public record AgendamentoResponseDTO(
        Long id,
        LocalDateTime inicio,
        LocalDateTime termino,
        Long clienteId,
        String clienteNome,
        Long servicoId,
        String servicoNome,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}