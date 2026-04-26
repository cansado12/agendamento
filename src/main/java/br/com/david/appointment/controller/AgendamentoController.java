package br.com.david.appointment.controller;

import br.com.david.appointment.DTO.AgendamentoResponseDTO;
import br.com.david.appointment.DTO.RequestAgendamentoDTO;
import br.com.david.appointment.service.AgendamentoService;
import br.com.david.shared.annotation.AgendamentoSwagger;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Agendamentos", description = "Endpoints para gerenciar agendamentos")
@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {




    private  final AgendamentoService  agendamentoService;

    @Autowired
    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    @AgendamentoSwagger.SwaggerInserirAgendamento
    public ResponseEntity<AgendamentoResponseDTO> inserir(@RequestBody @Valid RequestAgendamentoDTO agendamento) {
        AgendamentoResponseDTO criar = agendamentoService.criar(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(criar);
    }



    @GetMapping("/data/{data}")
    public ResponseEntity<Page<AgendamentoResponseDTO>> listarPorDia(
            @PathVariable LocalDate data,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "inicio") String ordem
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(ordem).ascending());

        return ResponseEntity.ok(agendamentoService.listarPorDia(data, pageRequest));
    }

    @GetMapping
    public ResponseEntity<Page<AgendamentoResponseDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "inicio") String ordem
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(ordem).ascending());

        return ResponseEntity.ok(agendamentoService.listarTodos(pageRequest));
    }



    @GetMapping("/{id}")
    @AgendamentoSwagger.SwaggerListarAgendamentoPorId
    public ResponseEntity<AgendamentoResponseDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.listarPorId(id));
    }

    @DeleteMapping("/{id}")
    @AgendamentoSwagger.SwaggerCancelarAgendamento
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {

        agendamentoService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @AgendamentoSwagger.SwaggerAtualizarAgendamento
    public ResponseEntity<AgendamentoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid RequestAgendamentoDTO agendamento) {
        return  ResponseEntity.ok(agendamentoService.atualizar(id, agendamento));


    }

    @GetMapping("/cliente/{id}")
    @AgendamentoSwagger.SwaggerBuscarClienteAgendado
    public ResponseEntity<Page<AgendamentoResponseDTO>> buscarClientePorId(@PathVariable Long id,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "inicio") String ordem) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(ordem).ascending());
        return ResponseEntity.ok(agendamentoService.listarPorCliente(id, pageRequest));


    }







}
