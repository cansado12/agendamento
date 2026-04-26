package br.com.david.appointment.controller;


import br.com.david.appointment.DTO.ClienteRequestDTO;
import br.com.david.appointment.DTO.ClienteResponseDTO;
import br.com.david.appointment.service.ClienteService;
import br.com.david.shared.annotation.ClienteSwagger;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clientes", description = "Endpoint para gereciamento de Clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService  clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @PostMapping
    @ClienteSwagger.SwaggerCreateCliente
    public ResponseEntity<ClienteResponseDTO> inserirCliente(@RequestBody @Valid ClienteRequestDTO cliente) {
        ClienteResponseDTO clienteResponseDTO = clienteService.create(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);


    }


    @GetMapping
    @ClienteSwagger.SwaggerListarClientes
    public ResponseEntity<Page<ClienteResponseDTO>> listarClientes(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   @RequestParam(defaultValue = "id") String ordem) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(ordem).ascending());

        return ResponseEntity.ok(clienteService.listarTodos(pageRequest));


    }

    @GetMapping("/{id}")
    @ClienteSwagger.SwaggerBuscarCliente
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }


}
