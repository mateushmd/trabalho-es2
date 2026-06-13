package com.clinica.controller;

import com.clinica.model.Atendimento;
import com.clinica.model.ExameLab;
import com.clinica.repository.ExameLabRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockbean.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExameLabController.class)
class ExameLabControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExameLabRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarExameLabComSucesso() throws Exception {
        Atendimento atendimento = new Atendimento();
        atendimento.setId(1L);

        ExameLab exame = new ExameLab();
        exame.setId(1L);
        exame.setDescricao("Hemograma");
        exame.setAtendimento(atendimento);

        when(repository.save(any(ExameLab.class))).thenReturn(exame);

        mockMvc.perform(post("/api/exames")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(exame)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value("Hemograma"));
    }

    @Test
    void deveListarExamesLab() throws Exception {
        ExameLab e1 = new ExameLab();
        e1.setId(1L);
        e1.setDescricao("Hemograma");

        when(repository.findAll()).thenReturn(Arrays.asList(e1));

        mockMvc.perform(get("/api/exames"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Hemograma"));
    }

    @Test
    void deveRetornar404ParaExameLabInexistente() throws Exception {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/exames/999"))
                .andExpect(status().isNotFound());
    }
}
