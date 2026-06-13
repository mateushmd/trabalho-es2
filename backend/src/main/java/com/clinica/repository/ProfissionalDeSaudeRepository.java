package com.clinica.repository;

import com.clinica.model.ProfissionalDeSaude;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfissionalDeSaudeRepository extends JpaRepository<ProfissionalDeSaude, Long> {
    List<ProfissionalDeSaude> findAllByOrderByNomeAsc();
    List<ProfissionalDeSaude> findByNomeContainingIgnoreCase(String nome);
    List<ProfissionalDeSaude> findByCategoriaContainingIgnoreCase(String categoria);
}
