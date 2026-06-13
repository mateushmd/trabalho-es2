package com.clinica.controller;

import com.clinica.model.Atendimento;
import com.clinica.model.ProfissionalDeSaude;
import com.clinica.repository.AtendimentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AtendimentoController.class)
class AtendimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AtendimentoRepository repository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void deveCriarAtendimentoComSucesso() throws Exception {
        ProfissionalDeSaude profissional = new ProfissionalDeSaude();
        profissional.setId(1L);
        profissional.setNome("Dr. João");

        Atendimento atendimento = new Atendimento();
        atendimento.setId(1L);
        atendimento.setData(LocalDate.of(2024, 12, 15));
        atendimento.setHorario(LocalTime.of(14, 0));
        atendimento.setProblemaTexto("Dor de cabeça");
        atendimento.setProfissionalDeSaude(profissional);

        when(repository.save(any(Atendimento.class))).thenReturn(atendimento);

        mockMvc.perform(post("/api/atendimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(atendimento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.problemaTexto").value("Dor de cabeça"));
    }

    @Test
    void deveListarAtendimentos() throws Exception {
        Atendimento a1 = new Atendimento();
        a1.setId(1L);
        a1.setProblemaTexto("Consulta rotina");
        a1.setData(LocalDate.of(2024, 12, 15));

        Atendimento a2 = new Atendimento();
        a2.setId(2L);
        a2.setProblemaTexto("Retorno");
        a2.setData(LocalDate.of(2024, 12, 15));

        when(repository.findAll()).thenReturn(Arrays.asList(a1, a2));

        mockMvc.perform(get("/api/atendimentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].problemaTexto").value("Consulta rotina"))
                .andExpect(jsonPath("$[1].problemaTexto").value("Retorno"));
    }

    @Test
    void deveRetornar404ParaAtendimentoInexistente() throws Exception {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/atendimentos/999"))
                .andExpect(status().isNotFound());
    }
}
