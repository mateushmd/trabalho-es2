package com.clinica.controller;

import com.clinica.model.ProfissionalDeSaude;
import com.clinica.repository.ProfissionalDeSaudeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfissionalDeSaudeController.class)
class ProfissionalDeSaudeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfissionalDeSaudeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarProfissionalComSucesso() throws Exception {
        ProfissionalDeSaude profissional = new ProfissionalDeSaude();
        profissional.setId(1L);
        profissional.setNome("Dr. João");
        profissional.setTelefone("31999999999");
        profissional.setCategoria(Arrays.asList("Cardiologia"));

        when(repository.save(any(ProfissionalDeSaude.class))).thenReturn(profissional);

        mockMvc.perform(post("/api/profissionais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profissional)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Dr. João"))
                .andExpect(jsonPath("$.categoria[0]").value("Cardiologia"));
    }

    @Test
    void deveListarProfissionaisVazio() throws Exception {
        when(repository.findAllByOrderByNomeAsc()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/profissionais"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void deveRetornar404ParaProfissionalInexistente() throws Exception {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/profissionais/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarProfissionalComSucesso() throws Exception {
        ProfissionalDeSaude profissional = new ProfissionalDeSaude();
        profissional.setId(1L);
        profissional.setNome("Dr. João");

        when(repository.findById(1L)).thenReturn(Optional.of(profissional));

        mockMvc.perform(delete("/api/profissionais/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Profissional removido"));
    }
}
