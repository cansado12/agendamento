package br.com.david.appointment.service;

import br.com.david.appointment.DTO.AgendamentoResponseDTO;
import br.com.david.appointment.DTO.RequestAgendamentoDTO;
import br.com.david.appointment.model.Agendamento;
import br.com.david.appointment.model.Cliente;
import br.com.david.appointment.model.Servico;
import br.com.david.appointment.model.Status;
import br.com.david.appointment.repository.AgendamentoRepository;
import br.com.david.appointment.repository.ClienteRepository;
import br.com.david.appointment.repository.ServicoRepository;
import br.com.david.shared.Mapper.AgendamentoMapper;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository repository;

    @InjectMocks
    private AgendamentoService service;

    @Mock
    private AgendamentoMapper  mapper;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ServicoRepository servicoRepository;

    private Agendamento agendamento;
    private AgendamentoResponseDTO  responseDTO;
    private RequestAgendamentoDTO requestDTO;
    private Pageable pageable;
    private Cliente cliente;
    private Servico servico;


    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
        agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setStatus(Status.AGENDADO);
        agendamento.setInicio(LocalDateTime.now());
        agendamento.setUpdatedAt(LocalDateTime.now());
        servico = new Servico();
        cliente = new Cliente();
        servico.setId(1L);
        servico.setPreco(new BigDecimal("30"));
        servico.setNome("barba");
        servico.setAtivo(true);
        servico.setDuracao(60);
        cliente.setId(1L);
        cliente.setNome("David");
        cliente.setTelefone("22999069944");



        requestDTO = new RequestAgendamentoDTO(LocalDateTime.now(), cliente.getId(), servico.getId());
        LocalDateTime inicio = requestDTO.inicio();
        LocalDateTime termino = inicio.plusMinutes(servico.getDuracao());

        responseDTO = new AgendamentoResponseDTO(1L, inicio, termino, cliente.getId(), cliente.getNome(), servico.getId(), servico.getNome(), Status.AGENDADO, LocalDateTime.now(), LocalDateTime.now());



    }

    @Test
    @DisplayName("criar - deve agendar cliente com servico")
    void criar() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(mapper.toAgendamento(requestDTO)).thenReturn(agendamento);
        when(repository.save(agendamento)).thenReturn(agendamento);
        when(mapper.toDTOAgendamento(agendamento)).thenReturn(responseDTO);

        AgendamentoResponseDTO result = service.criar(requestDTO);

        assertThat(result.clienteNome()).isEqualTo("David");
        verify(repository).save(agendamento);
    }

    @Test
    @DisplayName("listarPorDia - deve listar todos os agendamentos do dia")
    void listarPorDia() {
        LocalDate data = LocalDate.of(2025, 1, 15);
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.plusDays(1).atStartOfDay();

        Page<Agendamento> page = new PageImpl<>(List.of(agendamento));

        when(repository.findByInicioGreaterThanEqualAndInicioLessThan(inicio, fim, pageable))
                .thenReturn(page);
        when(mapper.toDTOAgendamento(agendamento))
                .thenReturn(responseDTO);

        Page<AgendamentoResponseDTO> result = service.listarPorDia(data, pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(responseDTO);
        verify(repository).findByInicioGreaterThanEqualAndInicioLessThan(inicio, fim, pageable);
    }

    @Test
    @DisplayName("listarPorId - deve listar agendamento pelo id")
    void listarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));
        when(mapper.toDTOAgendamento(agendamento)).thenReturn(responseDTO);

        AgendamentoResponseDTO result = service.listarPorId(1L);

        assertThat(result.clienteNome()).isEqualTo("David");
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("cancelar - cancela atendimento")
    void cancelar() {
        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));
        service.cancelar(1L);
        assertThat(agendamento.getStatus()).isEqualTo(Status.CANCELADO);
        verify(repository).save(agendamento);

    }


    @Test
    @DisplayName("atualizar - deve atualizar a agenda")
    void atualizar() {
        when(repository.findById(1L)).thenReturn(Optional.of(agendamento));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(repository.save(agendamento)).thenReturn(agendamento);
        when(mapper.toDTOAgendamento(agendamento)).thenReturn(responseDTO);

        AgendamentoResponseDTO result = service.atualizar(1L, requestDTO);

        assertThat(result.clienteNome()).isEqualTo("David");
        verify(repository).save(agendamento);
    }

    @Test
    @DisplayName("listarPorCliente - deve listar atendimentos do cliente")
    void listarPorCliente() {
        Page<Agendamento> page = new PageImpl<>(List.of(agendamento));

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(repository.findByClienteId(1L, pageable)).thenReturn(page);
        when(mapper.toDTOAgendamento(agendamento)).thenReturn(responseDTO);

        Page<AgendamentoResponseDTO> result = service.listarPorCliente(1L, pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0)).isEqualTo(responseDTO);
        verify(clienteRepository).findById(1L);
        verify(repository).findByClienteId(1L, pageable);
    }
}