package br.com.david.appointment.DTO;

import java.math.BigDecimal;

public record ServicoResponseDTO(Long id, String nome, Integer duracao, BigDecimal preco,  boolean ativo) {
}
