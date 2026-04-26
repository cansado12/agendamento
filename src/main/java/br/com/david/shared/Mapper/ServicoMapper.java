package br.com.david.shared.Mapper;

import br.com.david.appointment.DTO.ServicoRequestDTO;
import br.com.david.appointment.DTO.ServicoResponseDTO;
import br.com.david.appointment.model.Servico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServicoMapper {

    Servico toEntityServico(ServicoRequestDTO servicoDto);
    ServicoResponseDTO toDTOServico(Servico servico);


    List<Servico> toServicos(List<ServicoRequestDTO> servicoDto);
    List<ServicoResponseDTO> toDTOServico(List<Servico> servicoDto);

}
