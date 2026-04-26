package br.com.david.appointment.service;


import br.com.david.appointment.DTO.ServicoRequestDTO;
import br.com.david.appointment.DTO.ServicoResponseDTO;
import br.com.david.shared.Mapper.ServicoMapper;
import br.com.david.shared.exception.ResourceNotFoundException;
import br.com.david.appointment.model.Servico;
import br.com.david.appointment.repository.ServicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final ServicoMapper mapper;

    public ServicoService(ServicoRepository servicoRepository, ServicoMapper servicoMapper) {
        this.servicoRepository = servicoRepository;
        this.mapper = servicoMapper;
    }



    @Transactional
    public ServicoResponseDTO criar(ServicoRequestDTO servico) {
        Servico entity = mapper.toEntityServico(servico);


        entity = servicoRepository.save(entity);

        return mapper.toDTOServico(entity);

    }


    @Transactional(readOnly = true)
    public Page<ServicoResponseDTO> listarAtivos(Pageable pageable) {

        return servicoRepository.findByAtivoTrue(pageable).map(mapper::toDTOServico);



    }

    @Transactional(readOnly = true)
    public ServicoResponseDTO buscarPorId(Long id) {
        return servicoRepository.findByIdAndAtivoTrue(id).map(mapper::toDTOServico).orElseThrow(() -> new ResourceNotFoundException("Id nao encontrado" + id));
    }

    @Transactional
    public void deletar(Long id) {
        Servico servico = servicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id nao encontrado" + id));
        servico.setAtivo(false);
        servicoRepository.save(servico);
    }







}
