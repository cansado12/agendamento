package br.com.david.appointment.service;


import br.com.david.appointment.DTO.ClienteRequestDTO;
import br.com.david.appointment.DTO.ClienteResponseDTO;
import br.com.david.appointment.model.Cliente;
import br.com.david.appointment.repository.ClienteRepository;
import br.com.david.shared.Mapper.ClienteMapper;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {


    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteMapper mapper;



    private Cliente cliente;
    private ClienteRequestDTO requestDTO;
    private ClienteResponseDTO responseDTO;
    private Pageable pageable;






    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("joao");
        cliente.setTelefone("22999069944");
        responseDTO = new ClienteResponseDTO(1L, "joao", "22999069944", LocalDateTime.now());
        requestDTO = new ClienteRequestDTO("joao", "22999069944");





    }

    @Test
    @DisplayName("create - deve criar cliente")
    void create() {
        when(mapper.toEntity(requestDTO)).thenReturn(cliente);
        when(repository.save(cliente)).thenReturn(cliente);
        when(mapper.ToDTOCliente(cliente)).thenReturn(responseDTO);

        ClienteResponseDTO result = clienteService.create(requestDTO);

        assertThat(result.nome()).isEqualTo("joao");
        verify(repository).save(cliente);
    }

    @Test
    @DisplayName("listarTodos - deve listar todos os clientes")
    void listarTodos() {
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(cliente)));
        when(mapper.ToDTOCliente(cliente)).thenReturn(responseDTO);
        Page<ClienteResponseDTO> result = clienteService.listarTodos(pageable);

        assertThat(result).hasSize(1);
        verify(repository).findAll(pageable);

    }

    @Test
    @DisplayName("buscarPorId - deve buscar cliente por id")
    void buscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        when(mapper.ToDTOCliente(cliente)).thenReturn(responseDTO);

        ClienteResponseDTO result = clienteService.buscarPorId(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.nome()).isEqualTo("joao");

    }
}