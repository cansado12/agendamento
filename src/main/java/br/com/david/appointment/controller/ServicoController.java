package br.com.david.appointment.controller;


import br.com.david.appointment.DTO.ServicoRequestDTO;
import br.com.david.appointment.DTO.ServicoResponseDTO;
import br.com.david.appointment.service.ServicoService;
import br.com.david.shared.annotation.ServicoSwagger;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Servicos", description = "Endpoints para gerenciamento de servicos")
@RestController
@RequestMapping("/servicos")
public class ServicoController {


    private final ServicoService servicoService;


    @Autowired
    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    @ServicoSwagger.SwaggerSalvarServico
    public ResponseEntity<ServicoResponseDTO> salvar(@RequestBody  @Valid ServicoRequestDTO servicoRequestDTO) {
        ServicoResponseDTO criar = servicoService.criar(servicoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(criar);


    }

    @GetMapping
    @ServicoSwagger.SwaggerListarServicos
    public ResponseEntity<Page<ServicoResponseDTO>> listar(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "id") String ordem) {


        Pageable pageable = PageRequest.of(page, size, Sort.by(ordem).descending());

        return ResponseEntity.ok(servicoService.listarAtivos(pageable));


    }


    @GetMapping("/{id}")
    @ServicoSwagger.SwaggerBuscarServicoPorId
    public ResponseEntity<ServicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return  ResponseEntity.ok(servicoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @ServicoSwagger.SwaggerDeletarServico
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        servicoService.deletar(id);
       return ResponseEntity.noContent().build();
    }






}
