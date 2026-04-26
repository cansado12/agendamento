package br.com.david.appointment.service;

import br.com.david.appointment.DTO.ClienteRequestDTO;
import br.com.david.appointment.DTO.ClienteResponseDTO;
import br.com.david.shared.Mapper.ClienteMapper;
import br.com.david.shared.exception.BusinessException;
import br.com.david.shared.exception.ResourceNotFoundException;
import br.com.david.appointment.model.Cliente;
import br.com.david.appointment.repository.ClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteMapper mapper;

    private final ClienteRepository clienteRepository;


    public ClienteService(ClienteMapper mapper, ClienteRepository clienteRepository) {
        this.mapper = mapper;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO cliente) {
        Cliente entity = mapper.toEntity(cliente);

        if (clienteRepository.existsByTelefone(entity.getTelefone())) {
            throw new BusinessException("Telefone ja cadastrado");
        }

        entity = clienteRepository.save(entity);
        return mapper.ToDTOCliente(entity);

    }



    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> listarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(mapper::ToDTOCliente);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        return clienteRepository.findById(id).map(mapper::ToDTOCliente).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado" + id));

    }



}
