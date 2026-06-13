package com.clinica.repository;

import com.clinica.model.ExameLab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExameLabRepository extends JpaRepository<ExameLab, Long> {
    List<ExameLab> findByAtendimento_ProfissionalDeSaude_NomeContainingIgnoreCase(String nome);
    List<ExameLab> findByAtendimento_ProfissionalDeSaude_CategoriaContainingIgnoreCase(String categoria);
}
