package br.com.david.appointment.controller;

import br.com.david.appointment.DTO.ClienteRequestDTO;
import br.com.david.appointment.DTO.ClienteResponseDTO;
import br.com.david.appointment.repository.ClienteRepository;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

class ClienteControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ClienteRepository repository;


    @Autowired
    private ObjectMapper objectMapper;




    private ClienteRequestDTO requestValido;
    private ClienteResponseDTO  responseValido;


    @BeforeEach
    void setUp() {
        repository.deleteAll();

        requestValido = new ClienteRequestDTO("david", "22999069944");
        responseValido = new ClienteResponseDTO(1L, "david", "22999069944", LocalDateTime.now());

    }


    @Test
    @DisplayName("POST /clientes - deve criar produto e retornar 200")
    void deveriaCriarClienteComSucesso200() throws Exception {
        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("david"))
                .andExpect(jsonPath("$.telefone").value("22999069944"));

    }



    @Test
    @DisplayName("POST /clientes - body invalido deve retornar 400")
    void inserirCliente_bodyInvalido_deveRetornar400() throws Exception {
        ClienteRequestDTO requestDTO = new ClienteRequestDTO("", "22999069944");

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());


}


    @Test
    @DisplayName("GET /clientes - deve listar clientes e retornar 200")
    void listarClientes_deveRetornar200() throws Exception {

        mockMvc.perform(post("/clientes")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated());


        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    @DisplayName("GET /clientes/id - deve buscar por id e retornar 200")
    void buscarClientePorId_deveRetornar200() throws Exception {
        String resposta = mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(resposta).get("id").asLong();


        mockMvc.perform(get("/clientes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseValido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("david"))
                .andExpect(jsonPath("$.telefone").value("22999069944"));


    }


    @Test
    @DisplayName("GET /clientes/id - ID nao encontrado deve retornar 404")
    void buscarClientePorId_naoEncontrado_deveRetornar404() throws Exception {
        mockMvc.perform(get("/clientes/{id}", 33L)
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseValido)))
                .andExpect(status().isNotFound());

    }




}









