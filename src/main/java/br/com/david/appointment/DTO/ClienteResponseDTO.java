package br.com.david.appointment.DTO;

import java.time.LocalDateTime;

public record ClienteResponseDTO(Long id, String nome, String telefone, LocalDateTime createdAt) {
}
