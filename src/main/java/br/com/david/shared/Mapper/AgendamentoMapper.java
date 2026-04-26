package br.com.david.shared.Mapper;

import br.com.david.appointment.DTO.AgendamentoResponseDTO;
import br.com.david.appointment.DTO.RequestAgendamentoDTO;
import br.com.david.appointment.model.Agendamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgendamentoMapper {
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "servico", ignore = true)
    @Mapping(target = "termino", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Agendamento toAgendamento(RequestAgendamentoDTO dtoagendamento);

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nome", target = "clienteNome")
    @Mapping(source = "servico.id", target = "servicoId")
    @Mapping(source = "servico.nome", target = "servicoNome")
    AgendamentoResponseDTO toDTOAgendamento(Agendamento agendamento);



    List<Agendamento> toagendamentos(List<RequestAgendamentoDTO> dtoagendamento);
    List<AgendamentoResponseDTO> toDTO(List<Agendamento> agendamentoList);


}
