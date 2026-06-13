package com.clinica;

import com.clinica.model.Atendimento;
import com.clinica.model.ExameLab;
import com.clinica.model.ProfissionalDeSaude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void deveExecutarFluxoCompletoProfissional() throws Exception {
        // 1. CRIAR profissional
        ProfissionalDeSaude prof = new ProfissionalDeSaude();
        prof.setNome("Dra. Maria");
        prof.setTelefone("31988887777");
        prof.setCategoria(Arrays.asList("Pediatria"));

        MvcResult result = mockMvc.perform(post("/api/profissionais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prof)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Dra. Maria"))
                .andReturn();

        Long id = objectMapper.readTree(result.getResponse().getContentAsString())
                .get("id").asLong();

        // 2. BUSCAR profissional criado
        mockMvc.perform(get("/api/profissionais/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Dra. Maria"));

        // 3. ATUALIZAR profissional
        prof.setNome("Dra. Maria Silva");
        prof.setCategoria(Arrays.asList("Pediatria", "Geral"));

        mockMvc.perform(put("/api/profissionais/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prof)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Dra. Maria Silva"));

        // 4. DELETAR profissional
        mockMvc.perform(delete("/api/profissionais/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void deveVincularAtendimentoEExameAProfissional() throws Exception {
        // Criar profissional
        ProfissionalDeSaude prof = new ProfissionalDeSaude();
        prof.setNome("Dr. Pedro");
        prof.setTelefone("31977776666");

        MvcResult profResult = mockMvc.perform(post("/api/profissionais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prof)))
                .andExpect(status().isCreated())
                .andReturn();

        Long profId = objectMapper.readTree(
                profResult.getResponse().getContentAsString()).get("id").asLong();

        // Criar atendimento vinculado
        String atendimentoJson = String.format("""
            {
                "data": "2024-12-20",
                "horario": "12:00:00",
                "problemaTexto": "Dor nas costas",
                "profissionalDeSaude": {"id": %d}
            }
            """, profId);

        MvcResult atendimentoResult = mockMvc.perform(post("/api/atendimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(atendimentoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.problemaTexto").value("Dor nas costas"))
                .andReturn();

        Long atendimentoId = objectMapper.readTree(
                atendimentoResult.getResponse().getContentAsString()).get("id").asLong();

        // Criar exame lab vinculado ao atendimento
        String exameJson = String.format("""
            {
                "descricao": "Raio-X",
                "atendimento": {"id": %d}
            }
            """, atendimentoId);

        mockMvc.perform(post("/api/exames")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exameJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value("Raio-X"));
    }
}
