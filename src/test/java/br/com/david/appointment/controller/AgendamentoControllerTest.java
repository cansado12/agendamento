package br.com.david.appointment.controller;

import br.com.david.appointment.DTO.AgendamentoResponseDTO;
import br.com.david.appointment.DTO.RequestAgendamentoDTO;
import br.com.david.appointment.model.Cliente;
import br.com.david.appointment.model.Servico;
import br.com.david.appointment.model.Status;
import br.com.david.appointment.repository.AgendamentoRepository;
import br.com.david.appointment.repository.ClienteRepository;
import br.com.david.appointment.repository.ServicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AgendamentoRepository repository;


    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    private RequestAgendamentoDTO  requestValido;
    private AgendamentoResponseDTO   responseValido;
    private Cliente cliente;
    private Servico servico;




    @BeforeEach
    void setUp() {
        cliente = new Cliente();
    cliente.setTelefone("22999069944");
    cliente.setNome("david");
    clienteRepository.save(cliente);

    servico = new Servico();
    servico.setNome("barba");
    servico.setPreco(new BigDecimal("20"));
    servico.setDuracao(1000);
    servico.setAtivo(true);

    servicoRepository.save(servico);



        repository.deleteAll();

        requestValido = new RequestAgendamentoDTO(LocalDateTime.of(2026, 4, 14, 9, 0), cliente.getId(), servico.getId());
        responseValido = new AgendamentoResponseDTO(1L, LocalDateTime.of(2026, 4, 14, 9, 0),
                LocalDateTime.of(2026, 4, 14, 10, 0),
                cliente.getId(), cliente.getNome(), servico.getId(), servico.getNome(),Status.AGENDADO, LocalDateTime.now(), LocalDateTime.now());

    }

    @Test
    @DisplayName("POST /agendamentos - deve agendar cliente com servico e retornar 201")
    void deveCriarAgendamento201() throws Exception {
        mockMvc.perform(post("/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated());



    }

    @Test
    @DisplayName("POST /agendamentos - body invalido deve retornar 400")
    void bodyInvalidoRetorna400() throws Exception {
        RequestAgendamentoDTO requestAgendamentoDTO = new RequestAgendamentoDTO(LocalDateTime.of(2026, 4, 14, 9, 0), null, servico.getId());


        mockMvc.perform(post("/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestAgendamentoDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("GET /agendamentos - deve listar os agendamentos e retornar 200")
    void listarDeveRetornar200() throws Exception {
        mockMvc.perform(post("/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/agendamentos")
                .param("page", "0")
                .param("size", "10")
                .param("ordem", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));



    }


    @Test
    @DisplayName("GET /agendamentos/data/{data} - deve listar agendamentos pelo dia")
    void listarPorDiaRetorna200() throws Exception {
        String resposta = mockMvc.perform(post("/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

       String inicioStr = objectMapper.readTree(resposta).get("inicio").textValue();

       LocalDateTime inicio = LocalDateTime.parse(inicioStr);

       LocalDate data = inicio.toLocalDate();

        mockMvc.perform(get("/agendamentos/data/{data}", data.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].inicio").value("2026-04-14T09:00:00"));
    }




    @Test
    @DisplayName("GET /agendamentos/{id} = deve listar agendamentos por id e retornar 200")
    void listarPorIdDeveRetornar200() throws Exception {
       String resposta =  mockMvc.perform(post("/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();


       Long id = objectMapper.readTree(resposta).get("id").asLong();

       mockMvc.perform(get("/agendamentos/{id}", id))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.clienteNome").value("david"))
               .andExpect(jsonPath("$.servicoNome").value("barba"));
    }



    @Test
    @DisplayName("DELETE /agendamentos/{id} - deve cancelar o agendamento pelo id e retornar 204")
    void excluiAgendamentoPorIdERetorna204() throws Exception {
       String resposta =  mockMvc.perform(post("/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();


            Long id = objectMapper.readTree(resposta).get("id").asLong();

            mockMvc.perform(delete("/agendamentos/{id}", id))
                    .andExpect(status().isNoContent());


            mockMvc.perform(get("/agendamentos/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("CANCELADO"));





    }


    @Test
    @DisplayName("PUT /agendamentos/id - deve atualizar atualizar agendamento por id e retonar 200")
    void atualizarPorIdDeveRetornar200() throws Exception {
        String resposta = mockMvc.perform(post("/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(resposta).get("id").asLong();


        Cliente cliente1 = new Cliente();
        cliente1.setNome("jorge");
        cliente1.setTelefone("22999081125");
        clienteRepository.save(cliente1);

        RequestAgendamentoDTO requestNova = new RequestAgendamentoDTO(LocalDateTime.of(2026, 5, 14, 9, 0), cliente1.getId(), servico.getId());



        mockMvc.perform(put("/agendamentos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestNova)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteNome").value("jorge"));

    }


    @Test
    @DisplayName("GET /agendamentos/cliente/{id} - deve buscar agendamentos do cliente e retornar 200")
    void buscarClientePorIdERetornar200() throws Exception {


        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated());


        Cliente cliente2 = new Cliente();
        cliente2.setNome("jorge");
        cliente2.setTelefone("22999999999");
        clienteRepository.save(cliente2);


        RequestAgendamentoDTO requestOutro = new RequestAgendamentoDTO(
                LocalDateTime.of(2026, 5, 14, 10, 0),
                cliente2.getId(),
                servico.getId()
        );

        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestOutro)))
                .andExpect(status().isCreated());


        mockMvc.perform(get("/agendamentos/cliente/{id}", cliente.getId())
                        .param("page", "0")
                        .param("size", "10")
                        .param("ordem", "inicio"))
                .andExpect(status().isOk())


                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].clienteId").value(cliente.getId()))
                .andExpect(jsonPath("$.content[0].clienteNome").value("david"));
    }








}