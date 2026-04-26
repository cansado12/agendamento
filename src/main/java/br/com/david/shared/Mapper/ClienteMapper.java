package br.com.david.shared.Mapper;

import br.com.david.appointment.DTO.ClienteRequestDTO;
import br.com.david.appointment.DTO.ClienteResponseDTO;
import br.com.david.appointment.model.Cliente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteRequestDTO clienteDto);
    ClienteResponseDTO ToDTOCliente(Cliente cliente);

    List<Cliente> toClientes(List<ClienteRequestDTO> clienteDto);

    List<ClienteResponseDTO> ToDTOCliente(List<Cliente> clienteList);



}
