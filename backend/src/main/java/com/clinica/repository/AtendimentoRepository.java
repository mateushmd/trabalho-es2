package com.clinica.repository;

import com.clinica.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    List<Atendimento> findByProfissionalDeSaude_NomeContainingIgnoreCase(String nome);
    List<Atendimento> findByProfissionalDeSaude_CategoriaContainingIgnoreCase(String categoria);
}
