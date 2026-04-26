package br.com.david.appointment.service;

import br.com.david.appointment.DTO.AgendamentoResponseDTO;
import br.com.david.appointment.DTO.RequestAgendamentoDTO;
import br.com.david.shared.Mapper.AgendamentoMapper;
import br.com.david.shared.exception.BusinessException;
import br.com.david.shared.exception.ConflictException;
import br.com.david.shared.exception.ResourceNotFoundException;
import br.com.david.appointment.model.Agendamento;
import br.com.david.appointment.model.Cliente;
import br.com.david.appointment.model.Servico;
import br.com.david.appointment.model.Status;
import br.com.david.appointment.repository.AgendamentoRepository;
import br.com.david.appointment.repository.ClienteRepository;
import br.com.david.appointment.repository.ServicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AgendamentoService {

    private  final AgendamentoMapper mapper;

    private final AgendamentoRepository agendamentoRepository;
    private final ServicoRepository servicoRepository;
    private final ClienteRepository clienteRepository;

    public AgendamentoService(AgendamentoMapper mapper, AgendamentoRepository agendamentoRepository, ServicoRepository servicoRepository, ClienteRepository clienteRepository) {
        this.mapper = mapper;
        this.agendamentoRepository = agendamentoRepository;
        this.servicoRepository = servicoRepository;
        this.clienteRepository = clienteRepository;

    }


    @Transactional
    public AgendamentoResponseDTO criar(RequestAgendamentoDTO agendamento) {
        Cliente cliente = clienteRepository.findById(agendamento.clienteId()).orElseThrow(() -> new ResourceNotFoundException("Cliente nao encontrado"));
        Servico servico = servicoRepository.findById(agendamento.servicoId()).orElseThrow(() -> new ResourceNotFoundException("Servico nao encontrado"));






        LocalDateTime termino = agendamento.inicio().plusMinutes(servico.getDuracao());
        if (agendamentoRepository.existsByInicioLessThanAndTerminoGreaterThan(termino, agendamento.inicio())) {
            throw new ConflictException("Conflito no horario");
        }
        Agendamento entity = mapper.toAgendamento(agendamento);
        entity.setCliente(cliente);
        entity.setServico(servico);
        entity.setStatus(Status.AGENDADO);
        entity.setTermino(termino);
        entity = agendamentoRepository.save(entity);
        return mapper.toDTOAgendamento(entity);
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoResponseDTO> listarPorDia(LocalDate data, Pageable pageRequest) {

        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.plusDays(1).atStartOfDay();
        return agendamentoRepository.findByInicioGreaterThanEqualAndInicioLessThan(inicio, fim, pageRequest).map(mapper::toDTOAgendamento);

    }



    @Transactional(readOnly = true)
    public Page<AgendamentoResponseDTO> listarTodos(Pageable pageable) {
        return agendamentoRepository.findAll(pageable)
                .map(mapper::toDTOAgendamento);
    }



    @Transactional(readOnly = true)
    public AgendamentoResponseDTO listarPorId(Long id) {
        return agendamentoRepository.findById(id).map(mapper::toDTOAgendamento).orElseThrow(() -> new ResourceNotFoundException("Agendamento nao encontrado"));
    }

    @Transactional
    public void cancelar(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agendamento nao encontrado"));


        if (agendamento.getStatus() != Status.AGENDADO) {
            throw new BusinessException("Apenas agendamentos com status AGENDADO podem ser cancelados");
        }
        agendamento.setStatus(Status.CANCELADO);

        agendamentoRepository.save(agendamento);



    }

    @Transactional

    public AgendamentoResponseDTO atualizar(Long id, RequestAgendamentoDTO dto) {

        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Servico servico = servicoRepository.findById(dto.servicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado"));


        LocalDateTime termino = dto.inicio().plusMinutes(servico.getDuracao());
        boolean conflito = agendamentoRepository
                .existsByIdNotAndInicioLessThanAndTerminoGreaterThan(id,
                        termino,
                        dto.inicio()
                );

        if (conflito) {
            throw new ConflictException("Conflito de horário");
        }

        if (agendamento.getStatus() != Status.AGENDADO) {
            throw new BusinessException("Só é possível alterar agendamentos com status AGENDADO");
        }



        agendamento.setCliente(cliente);
        agendamento.setServico(servico);
        agendamento.setInicio(dto.inicio());
        agendamento.setTermino(termino);

        return mapper.toDTOAgendamento(agendamentoRepository.save(agendamento));
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoResponseDTO> listarPorCliente(Long clienteId, Pageable pageRequest) {
        clienteRepository.findById(clienteId).orElseThrow(() -> new ResourceNotFoundException("Cliente nao encontrado"));

       return agendamentoRepository.findByClienteId(clienteId,pageRequest).map(mapper::toDTOAgendamento);




    }






}
