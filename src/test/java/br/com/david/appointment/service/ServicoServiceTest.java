package br.com.david.appointment.service;


import br.com.david.appointment.DTO.ServicoRequestDTO;
import br.com.david.appointment.DTO.ServicoResponseDTO;
import br.com.david.appointment.model.Servico;
import br.com.david.appointment.repository.ServicoRepository;
import br.com.david.shared.Mapper.ServicoMapper;
import br.com.david.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ServicoServiceTest {

    @Mock
    private ServicoRepository repository;


    @InjectMocks
    private ServicoService service;


    @Mock
    private ServicoMapper mapper;



    private Servico servico;
    private ServicoResponseDTO responseDTO;
    private ServicoRequestDTO requestDTO;
    private Pageable pageable;



    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);

        servico = new Servico();
        servico.setId(1L);
        servico.setAtivo(true);
        servico.setNome("barba");
        servico.setDuracao(2000);
        servico.setPreco(new BigDecimal("30"));

        responseDTO = new ServicoResponseDTO(1L, "barba", 2000, new BigDecimal("30"), true);
        requestDTO = new ServicoRequestDTO("barba", 2000, new BigDecimal("30"), true);

    }

    @Test
    @DisplayName("criar - deve criar um servico")
    void criar() {
        when(mapper.toEntityServico(requestDTO)).thenReturn(servico);
        when(repository.save(servico)).thenReturn(servico);
        when(mapper.toDTOServico(servico)).thenReturn(responseDTO);

        ServicoResponseDTO result = service.criar(requestDTO);

        assertThat(result.nome()).isEqualTo("barba");
        verify(repository).save(servico);

    }

    @Test
    @DisplayName("listarAtivos - lista todos os servicos ativos")
    void listarAtivos() {
        when(repository.findByAtivoTrue(pageable)).thenReturn(new PageImpl<>(List.of(servico)));
        when(mapper.toDTOServico(servico)).thenReturn(responseDTO);
        Page<ServicoResponseDTO> result = service.listarAtivos(pageable);

        assertThat(result).hasSize(1);
        verify(repository).findByAtivoTrue(pageable);

    }

    @Test
    @DisplayName("bsucarPorId - deve buscar o servico pelo id")
    void buscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(servico));
        when(mapper.toDTOServico(servico)).thenReturn(responseDTO);

        ServicoResponseDTO result = service.buscarPorId(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.nome()).isEqualTo("barba");


    }

    @Test
    @DisplayName("deletar - deve deletar o servico")
    void deletar() {
        when(repository.findById(1L)).thenReturn(Optional.of(servico));
        service.deletar(1L);
        assertThat(servico.getAtivo()).isFalse();
        verify(repository).save(servico);

    }
}