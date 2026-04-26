package br.com.david.appointment.controller;

import br.com.david.appointment.DTO.ServicoRequestDTO;
import br.com.david.appointment.DTO.ServicoResponseDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServicoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;


    private ServicoResponseDTO  responseValido;
    private ServicoRequestDTO  requestValido;



    @BeforeEach
    void setUp() {
        repository.deleteAll();

        requestValido = new ServicoRequestDTO("barba", 2000, new BigDecimal("30"), true);
        responseValido = new ServicoResponseDTO(1L, "barba", 2000, new BigDecimal("30"), true);


    }

    @Test
    @DisplayName("POST /servicos - deve criar novo servico e retornar 201")
    void deveCriarServicoComSucesso201() throws Exception {
        mockMvc.perform(post("/servicos")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ativo").value(true))
                .andExpect(jsonPath("$.nome").value("barba"));


    }



    @Test
    @DisplayName("POST /servicos = body invalido deve retornar 400")
    void bodyInvalido400() throws Exception {

        ServicoRequestDTO requestDTO = new ServicoRequestDTO("", 100,new BigDecimal(21), true);

        mockMvc.perform(post("/servicos")
        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());


    }


    @Test
    @DisplayName("GET /servicos - deve listar servicos e retornar 200")
    void develistarRetonar200() throws Exception {
        mockMvc.perform(post("/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/servicos")
                        .param("page", "0")
                        .param("size", "10")
                        .param("ordem", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.content[0].nome").value("barba"));

    }

    @Test
    @DisplayName("GET /servicos/id - deve buscar por id e retornar 200")
    void buscarPorIdERetornar200() throws Exception {
        String resposta = mockMvc.perform(post("/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(resposta).get("id").asLong();

        mockMvc.perform(get("/servicos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseValido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("barba"))
                .andExpect(jsonPath("$.duracao").value(2000));

    }


    @Test
    @DisplayName("GET /servicos/id - ID invalido deve retornar 404")
    void buscarServicoComIDInvalidoDeveRetornar400() throws Exception {
        mockMvc.perform(get("/servicos/{id}", 33L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("DELETE /servicos/id - deve deletar o servico e retornar 204")
    void deveDeletarEretornar204() throws Exception {
        String resposta = mockMvc.perform(post("/servicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValido)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(resposta).get("id").asLong();


        mockMvc.perform(delete("/servicos/{id}", id))
                .andExpect(status().isNoContent());


        mockMvc.perform(get("/servicos/{id}", id))
                .andExpect(status().isNotFound());
    }


    



}